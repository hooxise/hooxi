package com.hooxi.event.ingestion.kafka;

import com.hooxi.data.model.event.HooxiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

//@Service
public class HooxiEventProducerService {
    private final Logger log = LoggerFactory.getLogger(HooxiEventProducerService.class);
    private final ReactiveKafkaProducerTemplate<String, HooxiEvent> reactiveKafkaProducerTemplate;

    @Value(value = "${hooxi_event_topic}")
    private String topic;

    public HooxiEventProducerService(ReactiveKafkaProducerTemplate<String, HooxiEvent> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    public Mono<SenderResult<Void>> send(HooxiEvent hooxiEvent) {
        log.info("send to topic={}, {}={},", topic, HooxiEvent.class.getSimpleName(), hooxiEvent);
        return reactiveKafkaProducerTemplate.send(topic, hooxiEvent)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", hooxiEvent, senderResult.recordMetadata().offset()));
    }

}
