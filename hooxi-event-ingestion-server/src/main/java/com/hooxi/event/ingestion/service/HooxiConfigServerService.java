package com.hooxi.event.ingestion.service;

import com.hooxi.data.model.config.FindDestinationsResponse;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class HooxiConfigServerService {

    private static final Logger logger = LoggerFactory.getLogger(HooxiConfigServerService.class);
    private final WebClient configServiceWebClient;
    private final URI configServerURI;


    public HooxiConfigServerService(@Value("${hooxi.config.server.url}") String configServerUrl) {
        configServiceWebClient = WebClient.builder().build();
        configServerURI = URI.create(configServerUrl);
    }

    public Mono<FindDestinationsResponse> findDestinations(HooxiEventEntity he) {
        logger.debug("base url " + configServiceWebClient);
      return configServiceWebClient
              .get()
              .uri(
                      uriBuilder ->
                              uriBuilder
                                      .host(configServerURI.getHost())
                                      .port(configServerURI.getPort())
                                      .scheme(configServerURI.getScheme())
                                      .path("/config/api/v1/tenants/{tenantId}/destmappings")
                                      .queryParam("domainId", he.getDomainId())
                                      .queryParam("subdomainId", he.getSubdomainId())
                                      .queryParam("eventType", he.getEventType())
                                      .build(he.getTenantId()))
              .retrieve()
              .bodyToMono(FindDestinationsResponse.class);
    }
}
