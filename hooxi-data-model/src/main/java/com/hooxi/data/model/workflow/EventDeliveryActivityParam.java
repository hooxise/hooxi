package com.hooxi.data.model.workflow;

import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.event.HooxiEvent;

import java.io.Serializable;

public class EventDeliveryActivityParam implements Serializable {
    HooxiEvent hooxiEvent;
    Destination eventDestination;
    Long destinationId;

    public EventDeliveryActivityParam() {
    }

    public EventDeliveryActivityParam(HooxiEvent hooxiEvent, Destination eventDestination, Long destinationId) {
        this.hooxiEvent = hooxiEvent;
        this.eventDestination = eventDestination;
        this.destinationId = destinationId;
    }

    public HooxiEvent getHooxiEvent() {
        return hooxiEvent;
    }

    public void setHooxiEvent(HooxiEvent hooxiEvent) {
        this.hooxiEvent = hooxiEvent;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public Destination getEventDestination() {
        return eventDestination;
    }

    public void setEventDestination(Destination eventDestination) {
        this.eventDestination = eventDestination;
    }


}
