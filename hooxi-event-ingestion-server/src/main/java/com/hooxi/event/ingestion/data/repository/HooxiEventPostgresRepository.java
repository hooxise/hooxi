package com.hooxi.event.ingestion.data.repository;

import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
//@ConditionalOnProperty(prefix = "hooxi", name = "persistence", havingValue = "postgres")
public interface HooxiEventPostgresRepository extends HooxiEventRepository, R2dbcRepository<HooxiEventEntity,String> {
}