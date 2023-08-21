package com.hooxi.event.ingestion;

import com.hooxi.collector.grpc.lib.HooxiEventReply;
import com.hooxi.collector.grpc.lib.HooxiEventRequest;
import com.hooxi.collector.grpc.lib.HooxiIngestorServiceGrpc;
import com.hooxi.data.model.event.HooxiEvent;
import com.hooxi.data.model.event.HooxiEventBuilder;
import com.hooxi.data.model.event.EventStatus;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import com.hooxi.event.ingestion.data.repository.HooxiEventRepository;
import com.hooxi.event.ingestion.kafka.HooxiEventProducerService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@GRpcService
@Component
public class HooxiGrpcService extends HooxiIngestorServiceGrpc.HooxiIngestorServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(HooxiGrpcService.class);
    @Autowired
    HooxiEventRepository hooxiEventRepository;

    //@Autowired
    HooxiEventProducerService hooxiEventProducerService;

    @Override
    public void sendEvent(HooxiEventRequest req, StreamObserver<HooxiEventReply> responseObserver) {
        HooxiEvent he = buildHooxiEvent(req);
        Mono<HooxiEvent> hooxiEventMono = persistAndSendToKafka(responseObserver, he,  Boolean.FALSE);
        hooxiEventMono.subscribe();
    }

    private Mono<HooxiEvent> persistAndSendToKafka(StreamObserver<HooxiEventReply> responseObserver, HooxiEvent he, boolean isResponseStream
    ) {
        HooxiEventEntity hooxiEventEntity = new HooxiEventEntity(he);
        Mono<HooxiEventEntity> hooxiEventMono = hooxiEventRepository.save(hooxiEventEntity).doOnSuccess(hePersisted -> {
            hooxiEventProducerService.send(hooxiEventEntity)
                    .doOnSuccess(senderResult -> {
                        hePersisted.setStatus(EventStatus.SENT_TO_WORKFLOW);
                        hooxiEventRepository.save(hePersisted);
                    })
                    .retry(3)
                    .doOnError(ex -> logger.error(
                            String.format("failed to send hooxi event %s with internal id %s to kafka",
                                    hePersisted.getExternalEventId(), hePersisted.getInternalEventId()), ex))
                    .doFinally(sg -> {
                        HooxiEventReply reply = HooxiEventReply.newBuilder()
                                .setEventId(hePersisted.getExternalEventId())
                                .setHooxiEventId(hePersisted.getInternalEventId())
                                .build();
                        responseObserver.onNext(reply);
                        if(!isResponseStream) {
                            responseObserver.onCompleted();
                        }
                    })
                    .subscribe();

        }).doOnError(err -> {
            logger.error("failed to process hooxi event {}", he, err);
            responseObserver.onError(err);
        });
        return hooxiEventMono.map(hooxiEventEntity1 -> (HooxiEvent)hooxiEventEntity1);
    }

    private HooxiEvent buildHooxiEvent(HooxiEventRequest req) {
        String hooxiInternalEventId = UUID.nameUUIDFromBytes(req.getEventId().getBytes(StandardCharsets.UTF_8)).toString();
        HooxiEvent he = HooxiEventBuilder.aHooxiEvent()
                .withInternalEventId(hooxiInternalEventId)
                .withExternalEventId(req.getEventId())
                .withEventSource(req.getEventMetadata().getEventSource())
                .withEventType(req.getEventMetadata().getEventType())
                .withEventURI(req.getEventMetadata().getEventURI())
                .withTenant(req.getEventMetadata().getTenant())
                .withTimestamp(req.getEventMetadata().getTimestamp())
                .withPayload(req.getPayload())
                .withStatus(EventStatus.PENDING)
                .withHeaders(req.getEventMetadata().getHeadersMap())
                .build();
        return he;
    }

    @Override
    public StreamObserver<HooxiEventRequest> sendEventStream(StreamObserver<HooxiEventReply> responseObserver) {
        return new StreamObserver<HooxiEventRequest>() {
            @Override
            public void onNext(HooxiEventRequest req) {
                HooxiEvent he = buildHooxiEvent(req);
                Mono<HooxiEvent> hooxiEventMono = persistAndSendToKafka(responseObserver, he, Boolean.TRUE);
                hooxiEventMono.subscribe();
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error received in hooxi grpc bidirectional stream", t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
