package com.hooxi.config.repository.redis;

import com.hooxi.config.exception.CacheMissException;
import com.hooxi.config.repository.entity.DestinationCacheEntity;
import com.hooxi.config.repository.entity.DestinationMappingEntity;
import com.hooxi.config.util.CacheUtils;
import com.hooxi.data.model.config.FindDestinationsRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisElementReader;
import org.springframework.data.redis.serializer.RedisElementWriter;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class DestinationRedisRepository {
  private final ReactiveRedisOperations<String, DestinationCacheEntity> template;
  private final RedisScript<List> findDestinationScript;
  private final RedisElementWriter<String> argsWriter;
  private final RedisElementReader resultReader;

  @Autowired
  public DestinationRedisRepository(
      ReactiveRedisOperations<String, DestinationCacheEntity> template,
      RedisScript<List> findDestinationScript,
      @Qualifier("findDestinationsArgsWriter") RedisElementWriter argsWriter,
      @Qualifier("findDestinationsResultReader") RedisElementReader resultReader) {
    this.template = template;
    this.findDestinationScript = findDestinationScript;
    this.argsWriter = argsWriter;
    this.resultReader = resultReader;
  }

  public Mono<List<DestinationCacheEntity>> findDestinations(FindDestinationsRequest req) {
    Flux<List> f =
        template.execute(
            findDestinationScript,
            new ArrayList<>(),
            Arrays.asList(
                req.getTenantId(), req.getDomain(), req.getSubDomain(), req.getEventType()),
            argsWriter,
            resultReader);
    return f.collect(
            ArrayList<DestinationCacheEntity>::new, (containerLst, lst) -> containerLst.addAll(lst))
        .map(
            lstResult -> {
              if (lstResult != null && !lstResult.isEmpty()) {
                return lstResult;
              } else {
                throw new CacheMissException("destination not found in cache");
              }
            });
  }

  public void addDestinationConfigToCache(List<DestinationCacheEntity> lstDestConfig) {
    // template.opsForList()
  }

  public Mono<Long> addDestinationConfigToCache(String key, DestinationCacheEntity dce) {
    return template.opsForList().leftPush(key, dce);
  }

  public Mono<Long> deleteFromCache(DestinationMappingEntity de, DestinationCacheEntity dce) {
    return template.opsForList().remove(CacheUtils.generateCacheKey(de), 0, dce);
  }
}
