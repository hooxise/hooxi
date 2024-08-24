package com.hooxi.config.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("api_key")
public class ApiKeyEntity {

    @Id
    @Column("api_key_id")
    private Long apiKeyId;

    @Column("api_key_hash")
    private String apiKeyHash;
    @Column("api_key_permissions")
    private String apiKeyPermissions;

    @Column("create_ts")
    private Long apiKeyCreateTS;
    @Column("expiration_ts")
    private Long apiKeyExpirationTS;

    @Column("api_key_name")
    private String apiKeyName;

    @Column("description")
    private String apiKeyDescription;

    @Column("created_by")
    private String createdBy;

    @Column("updated_by")
    private String updatedBy;

    @Column("update_ts")
    private Long apiKeyUpdateTS;

    @Column("is_active")
    private Boolean isActive;

    public Long getApiKeyId() {
        return apiKeyId;
    }

    public void setApiKeyId(Long apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    public String getApiKeyHash() {
        return apiKeyHash;
    }

    public void setApiKeyHash(String apiKeyHash) {
        this.apiKeyHash = apiKeyHash;
    }

    public String getApiKeyPermissions() {
        return apiKeyPermissions;
    }

    public void setApiKeyPermissions(String apiKeyPermissions) {
        this.apiKeyPermissions = apiKeyPermissions;
    }

    public Long getApiKeyCreateTS() {
        return apiKeyCreateTS;
    }

    public void setApiKeyCreateTS(Long apiKeyCreateTS) {
        this.apiKeyCreateTS = apiKeyCreateTS;
    }

    public Long getApiKeyExpirationTS() {
        return apiKeyExpirationTS;
    }

    public void setApiKeyExpirationTS(Long apiKeyExpirationTS) {
        this.apiKeyExpirationTS = apiKeyExpirationTS;
    }

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getApiKeyUpdateTS() {
        return apiKeyUpdateTS;
    }

    public void setApiKeyUpdateTS(Long apiKeyUpdateTS) {
        this.apiKeyUpdateTS = apiKeyUpdateTS;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
