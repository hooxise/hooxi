package com.hooxi.event.ingestion;

import com.hooxi.event.webhook.worker.EventPoller;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = {EventPoller.class, })
@ActiveProfiles("tc")
public class EventPollerTest {


}
