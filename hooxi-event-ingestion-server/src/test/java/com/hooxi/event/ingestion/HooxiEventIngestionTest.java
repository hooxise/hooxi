package com.hooxi.event.ingestion;

import com.hooxi.data.model.config.DestinationMappingResponseBuilder;
import com.hooxi.data.model.config.DestinationResponseBuilder;
import com.hooxi.data.model.config.FindDestinationsResponse;
import com.hooxi.data.model.config.FindDestinationsResponseBuilder;
import com.hooxi.data.model.dest.WebhookDestination;
import com.hooxi.event.ingestion.data.model.request.EventIngestionData;
import com.hooxi.event.ingestion.data.model.request.EventIngestionRequest;
import com.hooxi.event.ingestion.data.model.request.EventMetadata;
import com.hooxi.event.ingestion.service.HooxiConfigServerService;
import com.hooxi.event.webhook.worker.EventPoller;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

// @WebFluxTest
@SpringBootTest
@Testcontainers
@ActiveProfiles("tc")
public class HooxiEventIngestionTest {

  private static final Logger logger = LoggerFactory.getLogger(HooxiEventIngestionTest.class);
  @MockBean HooxiConfigServerService hooxiConfigServerService;

  @MockBean EventPoller eventPoller;

  WebTestClient webTestClient;
  @Autowired private ApplicationContext context;

  @Container
  static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:12");

  @Container
  static GenericContainer<?> redis =
      new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379);

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    redis.start();
    registry.add(
        "spring.r2dbc.url",
        () ->
            "r2dbc:postgresql://"
                + postgreSQLContainer.getHost()
                + ":"
                + postgreSQLContainer.getFirstMappedPort()
                + "/"
                + postgreSQLContainer.getDatabaseName());
    registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
    registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    registry.add("spring.data.redis.host", () -> redis.getHost());
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());
  }

  @BeforeEach
  public void setUp() {
    webTestClient = WebTestClient.bindToApplicationContext(context).build();
  }

  @Test
  void contextLoads() {}

  @Test
  public void shouldStoreEventToDB() throws Exception {
    WebhookDestination webDst = new WebhookDestination();
    webDst.setEndpoint("http://localhost/api/");
    webDst.setMetadata(new HashMap<>());
    Mockito.when(hooxiConfigServerService.findDestinations(Mockito.any()))
        .thenReturn(Mono.just(buildDestinationResponse(webDst)));

    webTestClient
        .post()
        .uri("/events")
        .bodyValue(buildTestEvent())
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.events.size()")
        .isEqualTo(1)
        .jsonPath("$.events[0].eventId")
        .isEqualTo("testEventId")
        .jsonPath("$.events[0].hooxiEventId")
        .isNotEmpty();
  }

  @NotNull
  private static FindDestinationsResponse buildDestinationResponse(WebhookDestination webDst) {
    return FindDestinationsResponseBuilder.aFindDestinationsResponse()
        .withDestinationMappings(
            List.of(
                DestinationMappingResponseBuilder.aDestinationMappingResponse()
                    .withDestinationMappingId(1L)
                    .withDomainId("domain1")
                    .withSubdomainId("subdomain1")
                    .withTenantId("tenant1")
                    .withEventType("eventType1")
                    .withDestinationInfo(
                        DestinationResponseBuilder.aDestinationResponse()
                            .withDestinationId(1L)
                            .withTenantId("tenant1")
                            .withDestination(webDst)
                            .build())
                    .build()))
        .build();
  }

  private EventIngestionRequest buildTestEvent() {
    EventMetadata eventMetadata =
        EventMetadata.EventMetadataBuilder.anEventMetadata()
            .withEventSource("TestSource")
            .withDomain("domain1")
            .withHeaders(Map.of("key1", "value1"))
            .withEventType("eventType1")
            .withEventURI("http://testevent/testeventId")
            .withSubdomain("subdomain1")
            .withTenant("tenant1")
            .withTimestamp(Instant.now().toEpochMilli())
            .build();
    EventIngestionRequest er =
        EventIngestionRequest.EventIngestionRequestBuilder.anEventIngestionRequest()
            .withEvents(
                List.of(
                    EventIngestionData.EventIngestionDataBuilder.anEventIngestionData()
                        .withEventId("testEventId")
                        .withEventMetadata(eventMetadata)
                        .withPayload("test payload")
                        .build()))
            .build();
    return er;
  }
}
