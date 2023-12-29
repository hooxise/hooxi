package com.hooxi.event.ingestion.data.repository;

import com.hooxi.event.ingestion.data.model.HooxiEventEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HooxiEventRepository extends ReactiveCrudRepository<HooxiEventEntity, String> {}
