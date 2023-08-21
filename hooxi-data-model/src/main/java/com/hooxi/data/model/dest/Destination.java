package com.hooxi.data.model.dest;

import java.util.HashMap;
import java.util.Map;

public abstract class Destination {
    private String endpoint;
    private Map<String, String> metadata;

    public Destination() {
        metadata = new HashMap<>();
    }

    public abstract DestinationType getDestinationType();

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }
}
