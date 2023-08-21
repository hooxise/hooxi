package com.hooxi.event.ingestion;

import com.hooxi.collector.grpc.lib.HooxiEventReply;
import com.hooxi.collector.grpc.lib.HooxiEventRequest;
import com.hooxi.collector.grpc.lib.HooxiIngestorServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HooxiGrpcClientService {

    private static final Logger logger = LoggerFactory.getLogger(HooxiGrpcClientService.class);

    @GrpcClient("hooxi-grpc-client")
    private HooxiIngestorServiceGrpc.HooxiIngestorServiceBlockingStub hooxiEventCollectorStub;

    public HooxiEventReply sendHooxiEvent(HooxiEventData hooxiEvent) {

        HooxiEventRequest req = HooxiEventRequest.newBuilder()
                .setEventId(hooxiEvent.getEventId())
                .setEventMetadata(HooxiEventRequest.EventMetadata.newBuilder()
                        .setEventSource(hooxiEvent.getEventSource())
                        .setEventType(hooxiEvent.getEventType())
                        .setEventURI(hooxiEvent.getEventURI())
                        .setTenant(hooxiEvent.getTenant())
                        .setTimestamp(hooxiEvent.getTimestamp())
                        .build())
                .setPayload(hooxiEvent.getPayload())
                .build();

        HooxiEventReply reply = hooxiEventCollectorStub.sendEvent(req);
        logger.debug("reply received " + reply);
        return reply;
    }


}
