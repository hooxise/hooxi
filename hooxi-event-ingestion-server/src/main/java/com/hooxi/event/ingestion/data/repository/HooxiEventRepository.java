package com.hooxi.event.ingestion.data.repository;

import com.hooxi.data.model.event.HooxiEvent;
import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HooxiEventRepository extends ReactiveCrudRepository<HooxiEventEntity, String> {
}
