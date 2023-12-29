package com.hooxi.event.webhook.worker;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.*;

@Configuration
public class HooxiEventPollerRedisConfiguration {

  @Bean
  public RedisScript<List<String>> pendingEventsScript() {
    DefaultRedisScript script = new DefaultRedisScript<>();
    script.setLocation(new ClassPathResource("redis/hooxi_pending_events.lua"));
    script.setResultType(List.class);
    return script;
  }

  @Bean
  public RedisScript<?> updateWebhookStatusScript() {
    DefaultRedisScript<?> script = new DefaultRedisScript<>();
    script.setLocation(new ClassPathResource("redis/hooxi_update_webhook_status.lua"));
    return script;
  }
}
