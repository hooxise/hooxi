package com.hooxi.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class HooxiReactiveRedisConfiguration {

  @Bean
  public RedisSerializer stringRedisSerializer() {
    return new StringRedisSerializer();
  }

  @Bean
  ReactiveRedisOperations<String, String> reactiveStringRedisTemplate(
      ReactiveRedisConnectionFactory factory, RedisSerializer stringRedisSerializer) {
    RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder =
        RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
    RedisSerializationContext<String, String> context =
        builder.key(stringRedisSerializer).value(stringRedisSerializer).build();

    return new ReactiveRedisTemplate<>(factory, context);
  }
}
