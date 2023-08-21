package com.hooxi.event.ingestion.data.repository;

import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

//@Repository
//@ConditionalOnProperty(prefix = "hooxi", name = "persistence", havingValue = "cassandra")
public interface HooxiEventCassandraRepository extends HooxiEventRepository, ReactiveCassandraRepository<HooxiEventEntity, String> {
}
