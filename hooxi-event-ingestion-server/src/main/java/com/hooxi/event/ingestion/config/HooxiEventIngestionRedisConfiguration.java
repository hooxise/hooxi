package com.hooxi.event.ingestion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class HooxiEventIngestionRedisConfiguration {

  @Bean
  public RedisScript<?> queueEventsScript() {
    DefaultRedisScript script = new DefaultRedisScript<>();
    script.setLocation(new ClassPathResource("redis/hooxi_add_events_to_queue.lua"));
    return script;
  }
}
