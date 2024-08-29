package com.hooxi.event.ingestion;

import static org.mockito.ArgumentMatchers.any;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.hooxi.data.model.config.DestinationResponse;
import com.hooxi.data.model.config.DestinationResponseBuilder;
import com.hooxi.data.model.config.DestinationSecurityConfigResponse;
import com.hooxi.data.model.config.DestinationSecurityConfigResponseBuilder;
import com.hooxi.data.model.dest.WebhookDestination;
import com.hooxi.event.ingestion.data.model.EventStatus;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import com.hooxi.event.ingestion.data.model.WebhookLogEntity;
import com.hooxi.event.webhook.worker.HooxiEventStatusUpdaterService;
import com.hooxi.event.webhook.worker.WebhookInvoker;
import com.hooxi.event.webhook.worker.exception.WebhookExecutionFailureException;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import java.net.HttpURLConnection;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = WebhookInvoker.class)
@EnableWireMock({@ConfigureWireMock(name = "webhook-service", property = "destination.url")})
public class WebhookInvokerTest {

  @InjectWireMock("webhook-service")
  private WireMockServer wiremock;

  @MockBean private HooxiEventStatusUpdaterService hooxiEventStatusUpdaterService;

  @Value("${destination.url}")
  private String destinationUrl;

  @Autowired private WebhookInvoker webhookInvoker;

  @NotNull
  private static HooxiEventEntity buildHooxiEventEntityForTest() {
    HooxiEventEntity testHe = new HooxiEventEntity();
    testHe.setStatus(EventStatus.PENDING);
    testHe.setDomainId("domain1");
    testHe.setSubdomainId("subdomain1");
    testHe.setEventSource("test-service");
    testHe.setEventType("event-type-1");
    testHe.setExternalEventId("external-id");
    testHe.setInternalEventId("internal-id");
    testHe.setPayload("test-payload-to-webhook");
    testHe.setVersion(1);
    return testHe;
  }

  @BeforeEach
  public void beforeEach() {
    wiremock.resetAll();
  }

  @Test
  void shouldCallWebhookThreeTimesWithBackoff() throws Exception {
    wiremock.stubFor(
        WireMock.post("/api/webhook")
            .inScenario("backoff test")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(
                ResponseDefinitionBuilder.responseDefinition()
                    .withStatus(HttpURLConnection.HTTP_INTERNAL_ERROR))
            .willSetStateTo("second attempt"));
    wiremock.stubFor(
        WireMock.post("/api/webhook")
            .inScenario("backoff test")
            .whenScenarioStateIs("second attempt")
            .willReturn(
                ResponseDefinitionBuilder.responseDefinition()
                    .withStatus(HttpURLConnection.HTTP_INTERNAL_ERROR))
            .willSetStateTo("third attempt"));
    wiremock.stubFor(
        WireMock.post("/api/webhook")
            .inScenario("backoff test")
            .whenScenarioStateIs("third attempt")
            .willReturn(ResponseDefinitionBuilder.okForEmptyJson())
            .willSetStateTo("third attempt"));

    HooxiEventEntity testHe = buildHooxiEventEntityForTest();

    DestinationResponse dr = buildDestinationResponseForTest();

    DestinationSecurityConfigResponse destSecConfig = buildDestSecConfigForTest();
    Mockito.when(hooxiEventStatusUpdaterService.saveWebhookLog(any()))
        .thenAnswer(
            (Answer<Mono<WebhookLogEntity>>)
                invocation -> Mono.just((WebhookLogEntity) invocation.getArguments()[0]));

    StepVerifier.create(webhookInvoker.invokeWebhookWithRetry(testHe, dr, destSecConfig))
        .expectSubscription()
        .expectNextMatches(o -> o.toString().contains("{}"))
        .verifyComplete();

    /*StepVerifier.withVirtualTime(() -> webhookInvoker.invokeWebhookWithRetry(testHe, dr))
    .expectSubscription()
    .expectNoEvent(Duration.ofSeconds(8))
    //.thenAwait(Duration.ofSeconds(4))
    .expectNext("{}")
    .verifyComplete();*/
    wiremock.verify(3, WireMock.postRequestedFor(WireMock.urlEqualTo("/api/webhook")));
  }

  @NotNull
  private static DestinationSecurityConfigResponse buildDestSecConfigForTest() {
    return DestinationSecurityConfigResponseBuilder.aDestinationSecurityConfigResponse()
        .withDestinationId(1l)
        .build();
  }

  @Test
  void shouldThrowErrorOnMaxRetries() throws Exception {
    wiremock.stubFor(
        WireMock.post("/api/webhook")
            .willReturn(
                ResponseDefinitionBuilder.responseDefinition()
                    .withStatus(HttpURLConnection.HTTP_INTERNAL_ERROR)));

    HooxiEventEntity testHe = buildHooxiEventEntityForTest();

    DestinationResponse dr = buildDestinationResponseForTest();

    DestinationSecurityConfigResponse destSecConfig = buildDestSecConfigForTest();
    Mockito.when(hooxiEventStatusUpdaterService.saveWebhookLog(any()))
        .thenAnswer(
            (Answer<Mono<WebhookLogEntity>>)
                invocation -> Mono.just((WebhookLogEntity) invocation.getArguments()[0]));
    StepVerifier.create(webhookInvoker.invokeWebhookWithRetry(testHe, dr, destSecConfig))
        .expectSubscription()
        .expectError(WebhookExecutionFailureException.class)
        .verify();

    wiremock.verify(3, WireMock.postRequestedFor(WireMock.urlEqualTo("/api/webhook")));
  }

  @Test
  void shouldSucceedWebhookInvocation() throws Exception {
    wiremock.stubFor(
        WireMock.post("/api/webhook")
            .willReturn(
                ResponseDefinitionBuilder.responseDefinition()
                    .withStatus(HttpURLConnection.HTTP_OK)
                    .withBody("STATUS OK")));

    HooxiEventEntity testHe = buildHooxiEventEntityForTest();

    DestinationResponse dr = buildDestinationResponseForTest();

    DestinationSecurityConfigResponse destSecConfig = buildDestSecConfigForTest();

    Mockito.when(hooxiEventStatusUpdaterService.saveWebhookLog(any()))
        .thenAnswer(
            (Answer<Mono<WebhookLogEntity>>)
                invocation -> Mono.just((WebhookLogEntity) invocation.getArguments()[0]));

    StepVerifier.create(webhookInvoker.invokeWebhookWithRetry(testHe, dr, destSecConfig))
        .expectSubscription()
        .expectNextMatches(o -> o.toString().contains("STATUS OK"))
        .verifyComplete();

    wiremock.verify(1, WireMock.postRequestedFor(WireMock.urlEqualTo("/api/webhook")));
  }

  @NotNull
  private DestinationResponse buildDestinationResponseForTest() {
    WebhookDestination webDst = new WebhookDestination();
    webDst.setEndpoint(wiremock.url("/api/webhook"));
    webDst.setMetadata(new HashMap<>());
    return DestinationResponseBuilder.aDestinationResponse().withDestination(webDst).build();
  }
}
