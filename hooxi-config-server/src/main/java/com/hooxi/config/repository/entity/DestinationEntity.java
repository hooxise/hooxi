package com.hooxi.config.repository.entity;

import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.dest.security.AuthenticationConfig;
import com.hooxi.data.model.dest.security.TLSConfig;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.StringJoiner;

@Table("hooxi_destination")
public class DestinationEntity {
    @Id
    private Long id;

    @Column("tenant_id")
    private String tenantId;

    @Column("destination")
    private Destination destination;

    @Column("tls_config")
    private TLSConfig tlsConfig;

    @Column("auth_config")
    private AuthenticationConfig authConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public TLSConfig getTlsConfig() {
        return tlsConfig;
    }

    public void setTlsConfig(TLSConfig tlsConfig) {
        this.tlsConfig = tlsConfig;
    }

    public AuthenticationConfig getAuthConfig() {
        return authConfig;
    }

    public void setAuthConfig(AuthenticationConfig authConfig) {
        this.authConfig = authConfig;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DestinationEntity.class.getSimpleName() + "[", "]").add("id=" + id)
            .add("tenantId='" + tenantId + "'")
            .add("destination=" + destination)
            .toString();
    }
}
