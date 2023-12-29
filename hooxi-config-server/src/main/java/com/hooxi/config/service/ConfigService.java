package com.hooxi.config.service;

import com.hooxi.config.exception.CacheMissException;
import com.hooxi.config.repository.DestinationMappingRepository;
import com.hooxi.config.repository.DestinationRepository;
import com.hooxi.config.repository.data.DestinationMapping;
import com.hooxi.config.repository.data.DestinationMappingBuilder;
import com.hooxi.config.repository.data.DestinationMappingStatus;
import com.hooxi.config.repository.entity.DestinationCacheEntity;
import com.hooxi.config.repository.entity.DestinationCacheEntityBuilder;
import com.hooxi.config.repository.entity.DestinationEntity;
import com.hooxi.config.repository.entity.DestinationMappingEntity;
import com.hooxi.config.repository.redis.DestinationRedisRepository;
import com.hooxi.config.util.CacheUtils;
import com.hooxi.data.model.config.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ConfigService {

  private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);

  private final DestinationRedisRepository destinationRedisRepository;
  private final DestinationRepository destinationRepository;
  private final DestinationMappingRepository destinationMappingRepository;

  @Autowired
  public ConfigService(
      DestinationRedisRepository destinationRedisRepository,
      DestinationRepository destinationRepository,
      DestinationMappingRepository destinationMappingRepository) {
    this.destinationRedisRepository = destinationRedisRepository;
    this.destinationRepository = destinationRepository;
    this.destinationMappingRepository = destinationMappingRepository;
  }

  public Mono<FindDestinationsResponse> findDestinations(Mono<FindDestinationsRequest> req) {
    return req.flatMap(
        (fdr) ->
            destinationRedisRepository
                .findDestinations(fdr)
                .onErrorResume(
                    CacheMissException.class,
                    (ce) -> findDestinationsAndUpdateCache(fdr).collectList())
                .map(
                    lstDstnCacheEntity ->
                        FindDestinationsResponseBuilder.aFindDestinationsResponse()
                            .withDestinationMappings(
                                lstDstnCacheEntity.stream()
                                    .map(
                                        dstnCacheEnt ->
                                            DestinationMappingResponseBuilder
                                                .aDestinationMappingResponse()
                                                .withTenantId(dstnCacheEnt.getTenantId())
                                                .withEventType(dstnCacheEnt.getEventType())
                                                .withDomainId(dstnCacheEnt.getDomainId())
                                                .withSubdomainId(dstnCacheEnt.getSubdomainId())
                                                .withDestinationMappingId(
                                                    dstnCacheEnt.getDestinationMappingId())
                                                .withDestinationInfo(
                                                    DestinationResponseBuilder
                                                        .aDestinationResponse()
                                                        .withTenantId(dstnCacheEnt.getTenantId())
                                                        .withDestinationId(
                                                            dstnCacheEnt.getDestinationId())
                                                        .withDestination(
                                                            dstnCacheEnt.getDestination())
                                                        .build())
                                                .withStatus(
                                                    dstnCacheEnt.getStatus() == null
                                                        ? ""
                                                        : dstnCacheEnt.getStatus().toString())
                                                .build())
                                    .collect(Collectors.toList()))
                            .build()));
  }

  private Flux<DestinationCacheEntity> findDestinationsAndUpdateCache(FindDestinationsRequest req) {
    logger.debug("searching in database for " + req);
    return destinationMappingRepository
        .findDestinationConfigFor(
            req.getTenantId(), req.getDomain(), req.getSubDomain(), req.getEventType())
        .log()
        .map(
            dstConfig ->
                DestinationCacheEntityBuilder.aDestinationCacheEntity()
                    .withTenantId(dstConfig.getTenantId())
                    .withDestination(dstConfig.getDestination())
                    .withDestinationId(dstConfig.getDestinationId())
                    .withStatus(dstConfig.getStatus())
                    .withDomainId(dstConfig.getDomainId())
                    .withSubdomainId(dstConfig.getSubdomainId())
                    .withEventType(dstConfig.getEventType())
                    .withDestinationMappingId(dstConfig.getDestinationMappingId())
                    .build())
        .flatMap(
            de -> {
              String key = CacheUtils.generateCacheKey(req);
              return destinationRedisRepository
                  .addDestinationConfigToCache(key, de)
                  .map(l -> de)
                  .onErrorContinue(
                      (err, dceForErr) ->
                          logger.error(
                              "error in storing DestinationCacheEntity {} in cache",
                              dceForErr,
                              err));
            });
  }

  public Mono<DestinationResponse> addDestination(
      Mono<AddDestinationRequest> dest, String tenantId) {
    return dest.flatMap(
        dstReq -> {
          DestinationEntity de = new DestinationEntity();
          de.setDestination(dstReq.getDestination());
          de.setTenantId(tenantId);
          de.setTlsConfig(dstReq.getTlsConfig());
          de.setAuthConfig(dstReq.getAuthConfig());
          return destinationRepository
              .save(de)
              .map(
                  savedDe -> {
                    DestinationResponse dstResp = new DestinationResponse();
                    dstResp.setDestinationId(savedDe.getId());
                    dstResp.setTenantId(savedDe.getTenantId());
                    dstResp.setDestination(savedDe.getDestination());
                    return dstResp;
                  });
        });
  }

  public Mono<DestinationResponse> findDestinationById(Long destinationId, String tenantId) {
    return destinationRepository
        .findByIdAndTenantId(destinationId, tenantId)
        .map(
            de ->
                DestinationResponseBuilder.aDestinationResponse()
                    .withDestinationId(de.getId())
                    .withTenantId(de.getTenantId())
                    .withDestination(de.getDestination())
                    .build());
  }

  public Flux<DestinationResponse> findDestinationForTenants(String teanntId) {
    return destinationRepository
        .findByTenantId(teanntId)
        .map(
            de ->
                DestinationResponseBuilder.aDestinationResponse()
                    .withDestinationId(de.getId())
                    .withTenantId(de.getTenantId())
                    .withDestination(de.getDestination())
                    .build());
  }

  public Mono<DestinationMappingResponse> addDestinationMapping(
      Mono<AddDestinationMappingRequest> dest, String tenantId) {
    return dest.flatMap(
        dstReq -> {
          DestinationMappingEntity de = new DestinationMappingEntity();
          de.setDestinationId(dstReq.getDestinationId());
          de.setTenantId(tenantId);
          de.setDomainId(dstReq.getDomainId());
          de.setSubdomainId(dstReq.getSubdomainId());
          de.setEventType(dstReq.getEventType());
          de.setStatus(DestinationMappingStatus.valueOf(dstReq.getStatus()));
          return destinationMappingRepository
              .save(de)
              .map(
                  savedDe -> {
                    DestinationMappingResponse dstResp = new DestinationMappingResponse();
                    dstResp.setDestinationMappingId(savedDe.getId());
                    dstResp.setTenantId(savedDe.getTenantId());
                    dstResp.setDomainId(savedDe.getDomainId());
                    dstResp.setSubdomainId(savedDe.getSubdomainId());
                    dstResp.setEventType(savedDe.getEventType());
                    dstResp.setStatus(savedDe.getStatus().toString());
                    return dstResp;
                  })
              .flatMap(
                  dstResp2 ->
                      destinationRepository
                          .findById(dstReq.getDestinationId())
                          .map(
                              dstn -> {
                                dstResp2.setDestinationInfo(
                                    DestinationResponseBuilder.aDestinationResponse()
                                        .withDestination(dstn.getDestination())
                                        .withDestinationId(dstn.getId())
                                        .withTenantId(dstn.getTenantId())
                                        .build());
                                return dstResp2;
                              }));
        });
  }

  public Mono<DestinationMapping> deleteDestinationMapping(String destMappingId) {
    return destinationMappingRepository
        .findById(Long.valueOf(destMappingId))
        .flatMap(
            destMappingEntity -> {
              return destinationMappingRepository
                  .updateDestinationMappingStatus(
                      destMappingEntity.getId(), DestinationMappingStatus.DELETED)
                  .onErrorStop()
                  .then(
                      destinationRepository
                          .findById(destMappingEntity.getDestinationId())
                          .flatMap(
                              dest -> {
                                DestinationCacheEntity dce =
                                    DestinationCacheEntityBuilder.aDestinationCacheEntity()
                                        .withTenantId(destMappingEntity.getTenantId())
                                        .withDestinationId(destMappingEntity.getDestinationId())
                                        .withDestination(dest.getDestination())
                                        .withStatus(destMappingEntity.getStatus())
                                        .build();
                                return destinationRedisRepository
                                    .deleteFromCache(destMappingEntity, dce)
                                    .map(
                                        l -> {
                                          logger.debug(
                                              "{} destination mapping deleted from cache", l);
                                          return DestinationMappingBuilder.aDestinationMapping()
                                              .withDestination(dest.getDestination())
                                              .withDestinationId(
                                                  destMappingEntity.getDestinationId())
                                              .withEventType(destMappingEntity.getEventType())
                                              .withDomainId(destMappingEntity.getDomainId())
                                              .withSubdomainId(destMappingEntity.getSubdomainId())
                                              .withStatus(destMappingEntity.getStatus())
                                              .build();
                                        });
                              }));
            });
  }
}
