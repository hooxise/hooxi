package com.hooxi.event.ingestion;

import com.hooxi.collector.grpc.lib.HooxiEventReply;
import com.hooxi.collector.grpc.lib.HooxiEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HooxiClientController {

    @Autowired
    private HooxiGrpcClientService hooxiGrpcClientService;

    @PostMapping("/send")
    public HooxiEventReply sendEvent(@RequestBody HooxiEventData hooxiEvent) {
        return hooxiGrpcClientService.sendHooxiEvent(hooxiEvent);
    }
}
