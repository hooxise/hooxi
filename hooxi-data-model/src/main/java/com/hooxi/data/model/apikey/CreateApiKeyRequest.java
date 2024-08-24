package com.hooxi.data.model.apikey;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CreateApiKeyRequest {

    private String apiKeyName;
    private String apiKeyDescription;
    private Long expirationTimestamp;
    @Schema(allowableValues = {"CONFIG", "CONFIG_RO", "EVENTS", "EVENT_RO"})
    private List<String> apiKeyPermissions;

    public String getApiKeyName() {
        return apiKeyName;
    }

    public void setApiKeyName(String apiKeyName) {
        this.apiKeyName = apiKeyName;
    }

    public String getApiKeyDescription() {
        return apiKeyDescription;
    }

    public void setApiKeyDescription(String apiKeyDescription) {
        this.apiKeyDescription = apiKeyDescription;
    }

    public Long getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Long expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public List<String> getApiKeyPermissions() {
        return apiKeyPermissions;
    }

    public void setApiKeyPermissions(List<String> apiKeyPermissions) {
        this.apiKeyPermissions = apiKeyPermissions;
    }
}
