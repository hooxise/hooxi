package com.hooxi.event.webhook.worker;

import com.hooxi.data.model.config.DestinationResponse;
import com.hooxi.data.model.config.DestinationSecurityConfigResponse;
import com.hooxi.data.model.dest.security.TLSConfig;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import com.hooxi.event.ingestion.data.model.WebhookFailureLogEntity;
import com.hooxi.event.webhook.worker.exception.WebhookExecutionFailureException;
import io.netty.handler.ssl.SslContext;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.net.ssl.SSLException;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.netty.util.NettySslUtils;
import nl.altindag.ssl.pem.util.PemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

@Component
public class WebhookInvoker {

  private static final Logger logger = LoggerFactory.getLogger(WebhookInvoker.class);
  private final HooxiEventStatusUpdaterService hooxiEventStatusUpdaterService;

  public WebhookInvoker(HooxiEventStatusUpdaterService hooxiEventStatusUpdaterService) {
    this.hooxiEventStatusUpdaterService = hooxiEventStatusUpdaterService;
  }

  @Transactional
  public Mono<String> invokeWebhookWithRetry(
      HooxiEventEntity he,
      DestinationResponse dst,
      DestinationSecurityConfigResponse dstSecConfig) {
    WebClient.Builder webClientBuilder = WebClient.builder();
    logger.debug("invoking webhook for event id " + he.getInternalEventId());
    if (dstSecConfig != null && dstSecConfig.getDestinationSecurityConfig() != null) {
      TLSConfig tlsConfig = dstSecConfig.getDestinationSecurityConfig().getTlsConfig();
      if (tlsConfig != null
          && ((tlsConfig.getCaCert() != null)
              || (tlsConfig.getPrivateKey() != null && tlsConfig.getPublicKey() != null))) {
        String pvtKeyPasswd = tlsConfig.getPassword();

        X509ExtendedKeyManager keyManager = null;
        if (pvtKeyPasswd != null && !pvtKeyPasswd.trim().isEmpty()) {
          keyManager =
              PemUtils.loadIdentityMaterial(
                  new ByteArrayInputStream(tlsConfig.getPublicKey().getBytes()),
                  new ByteArrayInputStream(tlsConfig.getPrivateKey().getBytes()),
                  pvtKeyPasswd.toCharArray());
        } else {
          keyManager =
              PemUtils.loadIdentityMaterial(
                  new ByteArrayInputStream(tlsConfig.getPublicKey().getBytes()),
                  new ByteArrayInputStream(tlsConfig.getPrivateKey().getBytes()));
        }

        X509ExtendedTrustManager trustManager =
            PemUtils.loadTrustMaterial(new ByteArrayInputStream(tlsConfig.getCaCert().getBytes()));
        SSLFactory sslFactory =
            SSLFactory.builder()
                .withIdentityMaterial(keyManager)
                .withTrustMaterial(trustManager)
                .build();
        try {
          SslContext sslContext = NettySslUtils.forClient(sslFactory).build();
          HttpClient httpClient =
              HttpClient.create().secure(sslSpec -> sslSpec.sslContext(sslContext));

          webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
        } catch (SSLException ex) {
          logger.error("failed to create http client for event id {}", he.getInternalEventId(), ex);
          throw new WebhookExecutionFailureException("failed to create http client with ssl", ex);
        }
      }
    }

    WebClient webClient = webClientBuilder.build();
    return webClient
        .post()
        .uri(dst.getDestination().getEndpoint())
        .bodyValue(he.getPayload())
        .exchangeToMono(
            clientResponse -> {
              StringBuilder sb = new StringBuilder();
              sb.append("status :");
              HttpStatusCode respStatus = clientResponse.statusCode();
              sb.append(respStatus.value()).append("\n");
              sb.append("headers :").append("\n");
              ;
              clientResponse
                  .headers()
                  .asHttpHeaders()
                  .forEach(
                      (h, hvalue) -> {
                        sb.append(h).append(":").append(hvalue).append("\n");
                        ;
                      });
              String responseHeaders = sb.toString();
              Mono<String> responseBodyMono = clientResponse.bodyToMono(String.class);
              if (respStatus.is2xxSuccessful()) {
                return responseBodyMono;
              } else {
                WebhookFailureLogEntity webhookFailureLogEntity = new WebhookFailureLogEntity();
                webhookFailureLogEntity.setExternalEventId(he.getExternalEventId());
                webhookFailureLogEntity.setInternalEventId(he.getInternalEventId());
                webhookFailureLogEntity.setTimestamp(System.currentTimeMillis());
                webhookFailureLogEntity.setHttpStatus(respStatus.value());
                webhookFailureLogEntity.setResponseHeaders(responseHeaders);
                return responseBodyMono.flatMap(
                    respBody -> {
                      webhookFailureLogEntity.setResponsePayload(respBody);
                      return hooxiEventStatusUpdaterService
                          .saveWebhookFailure(webhookFailureLogEntity)
                          .flatMap(
                              wfe ->
                                  Mono.error(
                                      new WebhookExecutionFailureException("non 2xx response")));
                    });
              }
              // return Mono.error(new WebhookExecutionFailureException("non 2xx response"));
            })
        .onErrorResume(Mono::error)
        .retryWhen(
            Retry.backoff(2, Duration.of(2, ChronoUnit.SECONDS))
                .onRetryExhaustedThrow(
                    (retryBackoffSpec, retrySignal) -> {
                      throw new WebhookExecutionFailureException(
                          "webhook execution failed for " + dst.getDestinationId());
                    }));
  }
}
