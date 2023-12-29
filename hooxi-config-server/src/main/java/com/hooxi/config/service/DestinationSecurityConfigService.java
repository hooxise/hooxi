package com.hooxi.config.service;

import com.hooxi.config.repository.DestinationRepository;
import com.hooxi.data.model.dest.security.DestinationSecurityConfig;
import com.hooxi.data.model.dest.security.DestinationSecurityConfigBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DestinationSecurityConfigService {

  private final DestinationRepository destinationRepository;

  @Autowired
  public DestinationSecurityConfigService(DestinationRepository destinationRepository) {
    this.destinationRepository = destinationRepository;
  }

  public Mono<DestinationSecurityConfig> findDestinationSecurityConfig(
      String tenantId, Long destinationId) {
    return destinationRepository
        .findByIdAndTenantId(destinationId, tenantId)
        .map(
            de ->
                DestinationSecurityConfigBuilder.aDestinationSecurityConfig()
                    .withAuthConfig(de.getAuthConfig())
                    .withTlsConfig(de.getTlsConfig())
                    .build());
  }
}
