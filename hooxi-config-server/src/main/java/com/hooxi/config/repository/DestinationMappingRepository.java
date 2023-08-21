package com.hooxi.config.repository;

import com.hooxi.config.repository.data.DestinationMapping;
import com.hooxi.config.repository.data.DestinationMappingStatus;
import com.hooxi.config.repository.entity.DestinationMappingEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DestinationMappingRepository extends ReactiveCrudRepository<DestinationMappingEntity, Long> {
    @Query("SELECT hdc.id as destmappingid, hdc.tenant_id, hdc.domain_id, hdc.subdomain_id, hdc.event_type, hd.destination, hdc.destination_id, hdc.status " +
        "FROM hooxi_destination hd, hooxi_destination_config hdc where hd.id = hdc.destination_id " +
        "and hdc.tenant_id = :tenantId and (hdc.domain_id = :domainId or hdc.domain_id is null) " +
        "and (hdc.subdomain_id = :subdomainId or hdc.subdomain_id is null) " +
        "and (hdc.event_type = :eventType or hdc.event_type is null)")
    Flux<DestinationMapping> findDestinationConfigFor(String tenantId, String domainId, String subdomainId,
                                                      String eventType);

    @Query("SELECT hdc.id as destmappingid, hdc.tenant_id, hdc.domain_id, hdc.subdomain_id, hdc.event_type, hd.destination, hdc.destination_id, hdc.status " +
        "FROM hooxi_destination hd, hooxi_destination_config hdc where hd.id = hdc.destination_id " +
        "and hdc.tenant_id = :tenantId and (hdc.domain_id = :domainId or hdc.domain_id is null) " +
        "and (hdc.subdomain_id = :subdomainId or hdc.subdomain_id is null) " + "and hdc.event_type is null")
    Flux<DestinationMapping> findDestinationConfigFor(String tenantId, String domainId, String subdomainId);

    @Query("SELECT hdc.id as destmappingid, hdc.tenant_id, hdc.domain_id, hdc.subdomain_id, hdc.event_type, hd.destination, hdc.destination_id, hdc.status " +
        "FROM hooxi_destination hd, hooxi_destination_config hdc where hd.id = hdc.destination_id " +
        "and hdc.tenant_id = :tenantId and (hdc.domain_id = :domainId or hdc.domain_id is null) " +
        "and hdc.subdomain_id is null " + "and hdc.event_type is null")
    Flux<DestinationMapping> findDestinationConfigFor(String tenantId, String domainId);

    @Query("SELECT hdc.id as destmappingid, hdc.tenant_id, hdc.domain_id, hdc.subdomain_id, hdc.event_type, hd.destination, hdc.destination_id, hdc.status " +
        "FROM hooxi_destination hd, hooxi_destination_config hdc where hd.id = hdc.destination_id " +
        "and hdc.tenant_id = :tenantId and hdc.domain_id is null " + "and hdc.subdomain_id is null " +
        "and hdc.event_type is null")
    Flux<DestinationMapping> findDestinationConfigFor(String tenantId);

    @Modifying
    @Query("UPDATE hooxi_destination_config set status = :updatedStatus where id = :destinationMappingId ")
    Mono<Integer> updateDestinationMappingStatus(Long destinationMappingId, DestinationMappingStatus updateStatus);

}
