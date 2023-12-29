// package com.hooxi.event.ingestion;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
// import com.datastax.driver.core.Cluster;
// import com.datastax.driver.core.Session;
// import com.hooxi.collector.grpc.lib.HooxiEventReply;
// import com.hooxi.collector.grpc.lib.HooxiEventRequest;
// import com.hooxi.collector.grpc.lib.HooxiIngestorServiceGrpc;
// import com.hooxi.data.model.event.HooxiEvent;
// import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
// import com.hooxi.event.ingestion.data.repository.HooxiEventRepository;
// import java.util.List;
// import org.junit.jupiter.api.BeforeAll;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.annotation.DirtiesContext;
// import org.springframework.test.context.ActiveProfiles;
// import org.testcontainers.containers.CassandraContainer;
// import org.testcontainers.containers.KafkaContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.testcontainers.utility.DockerImageName;
//
/// *@SpringBootTest(properties = {"grpc.server.inProcessName=hooxi-grpc", // Enable inProcess
// server
//        "grpc.server.port=-1", // Disable external server
//        "grpc.client.inProcess.address=in-process:hooxi-grpc" // Configure the client to connect
// to the inProcess server
// })*/
//// @SpringJUnitConfig(classes = {HooxiGrpcTestConfig.class})
// @DirtiesContext
// @Testcontainers
// @ActiveProfiles("test")
/// *@ContextConfiguration(
//        initializers = CassandraTestInitializer.class
// )*/
// public class HooxiEventIngestionGrpcTest {
//
//  private static final String KEYSPACE_NAME = "test";
//
//  private static final Logger logger = LoggerFactory.getLogger(HooxiEventIngestionGrpcTest.class);
//
//  // @GrpcClient("inProcess")
//  private HooxiIngestorServiceGrpc.HooxiIngestorServiceBlockingStub hooxiIngestorService;
//
//  @Container
//  public static final CassandraContainer cassandra =
//      (CassandraContainer) new CassandraContainer("cassandra:3.11.2").withExposedPorts(9042);
//
//  @Container
//  public static final KafkaContainer kafka =
//      new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
//
//  @Autowired HooxiEventRepository hooxiEventRepository;
//
//  @BeforeAll
//  static void setupProperties() {
//    System.setProperty("spring.data.cassandra.keyspace-name", KEYSPACE_NAME);
//    System.setProperty("spring.data.cassandra.contact-points", "localhost");
//    System.setProperty("spring.data.cassandra.port",
// String.valueOf(cassandra.getMappedPort(9042)));
//    System.setProperty("spring.kafka.bootstrap-servers", kafka.getBootstrapServers());
//    createKeyspace(cassandra.getCluster());
//  }
//
//  static void createKeyspace(Cluster cluster) {
//    try (Session session = cluster.connect()) {
//      session.execute(
//          "CREATE KEYSPACE IF NOT EXISTS "
//              + KEYSPACE_NAME
//              + " WITH replication = \n"
//              + "{'class':'SimpleStrategy','replication_factor':'1'};");
//      session.execute(
//          "CREATE TABLE IF NOT EXISTS test.hooxi_event (eventsource text, eventtype text, eventuri
// text, externaleventid text, internaleventid text, payload text, tenant text, timestamp text,
// status text, headers map<text,text>, createcloudevent boolean, PRIMARY KEY (internaleventid));");
//    }
//  }
//
//  void createKeyspace() {
//
//    // cqlTemplate.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication =
//    // \n" +
//    //       "{'class':'SimpleStrategy','replication_factor':'1'};");
//    // cqlTemplate.execute("CREATE TABLE IF NOT EXISTS .hooxi_event (eventsource text, eventtype
//    // text, eventuri text, externaleventid text, internaleventid text, payload text, tenant text,
//    // timestamp text, PRIMARY KEY (internaleventid));");
//
//  }
//
//  // @Test
//  @DirtiesContext
//  public void testHooxiSendEvent() throws Exception {
//    // createKeyspace();
//    HooxiEventRequest req =
//        HooxiEventRequest.newBuilder()
//            .setEventId("testEvent1")
//            .setEventMetadata(
//                HooxiEventRequest.EventMetadata.newBuilder()
//                    .setEventSource("testSource")
//                    .setEventType("TestEventType")
//                    .setEventURI("https://testgrpc.test/testSource")
//                    .setTenant("TestTenant1")
//                    .setTimestamp("2021-10-03T10:15:30+0100")
//                    .putHeaders("mediaType", "application/json")
//                    .build())
//            .setPayload("{\"name\":\"hooxi\"}")
//            .build();
//    HooxiEventReply reply = hooxiIngestorService.sendEvent(req);
//    logger.debug(
//        "response received for event id {}, hooxiEventId {}",
//        reply.getEventId(),
//        reply.getHooxiEventId());
//    assertThat(reply.getEventId()).isEqualTo("testEvent1");
//    assertThat(reply.getHooxiEventId()).isNotEmpty();
//    List<HooxiEventEntity> persistedHooxiEvents =
//        hooxiEventRepository.findAll().collectList().block().stream()
//            .peek(System.out::println)
//            .toList();
//    assertThat(persistedHooxiEvents.size()).isEqualTo(1);
//    HooxiEvent he = persistedHooxiEvents.get(0);
//    assertThat(he.getHeaders().get("mediaType")).isEqualTo("application/json");
//    assertThat(he.getHeaders().size()).isEqualTo(1);
//    assertThat(he.getEventSource()).isEqualTo("testSource");
//    assertThat(he.getEventType()).isEqualTo("TestEventType");
//    assertThat(he.getEventURI()).isEqualTo("https://testgrpc.test/testSource");
//    assertThat(he.getTenant()).isEqualTo("TestTenant1");
//    assertThat(he.getTimestamp()).isEqualTo("2021-10-03T10:15:30+0100");
//  }
// }
