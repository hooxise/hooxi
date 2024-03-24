package com.hooxi.event.webhook.worker.data;

import java.util.List;

public class WebhookAndEvents {
    private String webhookId;
    private List<String> eventIds;

    public String getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(String webhookId) {
        this.webhookId = webhookId;
    }

    public List<String> getEventIds() {
        return eventIds;
    }

    public void setEventIds(List<String> eventIds) {
        this.eventIds = eventIds;
    }
}
