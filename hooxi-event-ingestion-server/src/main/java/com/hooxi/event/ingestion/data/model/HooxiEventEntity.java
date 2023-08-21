package com.hooxi.event.ingestion.data.model;

import com.hooxi.data.model.event.HooxiEvent;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "hooxi_event")
public class HooxiEventEntity extends HooxiEvent {

    public HooxiEventEntity() {
    }

    public HooxiEventEntity(HooxiEvent he) {
        setInternalEventId(he.getInternalEventId());
        super.setEventSource(he.getEventSource());
        super.setEventType(he.getEventType());
        super.setEventURI(he.getEventURI());
        super.setCreateCloudEvent(he.isCreateCloudEvent());
        super.setExternalEventId(he.getExternalEventId());
        super.setPayload(he.getPayload());
        super.setStatus(he.getStatus());
        super.setTenant(he.getTenant());
        super.setTimestamp(he.getTimestamp());
        super.setHeaders(he.getHeaders());

    }

    @Override
    @PrimaryKey
    public String getInternalEventId() {
        return super.getInternalEventId();
    }

    @Override
    public void setInternalEventId(String internalEventId) {
        super.setInternalEventId(internalEventId);
    }

}
