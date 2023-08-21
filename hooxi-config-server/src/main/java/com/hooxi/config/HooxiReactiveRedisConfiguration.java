package com.hooxi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hooxi.config.repository.entity.DestinationCacheEntity;
import com.hooxi.config.repository.redis.DestinationRedisSerializer;
import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.json.deserialization.DestinationDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.*;

import java.util.List;

@Configuration
public class HooxiReactiveRedisConfiguration {


    @Bean
    ReactiveRedisOperations<String, DestinationCacheEntity> redisOperations(ReactiveRedisConnectionFactory factory,
                                                                            ObjectMapper mapper,
                                                                            RedisSerializer stringRedisSerializer) {
        Jackson2JsonRedisSerializer<DestinationCacheEntity> serializer = new Jackson2JsonRedisSerializer<>(
            DestinationCacheEntity.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, DestinationCacheEntity> builder = RedisSerializationContext.newSerializationContext(
            new DestinationRedisSerializer(mapper));

        RedisSerializationContext<String, DestinationCacheEntity> context = builder.key(stringRedisSerializer)
            .value(serializer)
            .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public RedisScript<List> findDestinationScript() {
        DefaultRedisScript<List> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("findDestinations.lua"));
        script.setResultType(List.class);
        return script;
    }

    @Bean
    public RedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        simpleModule.addDeserializer(Destination.class, new DestinationDeserializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }

    @Bean
    public RedisSerializer destRedisSerializer(ObjectMapper mapper) {
        return new DestinationRedisSerializer(mapper);
    }

    @Bean
    public RedisElementWriter findDestinationsArgsWriter(RedisSerializer stringRedisSerializer) {
        return RedisElementWriter.from(stringRedisSerializer);
    }

    @Bean
    public RedisElementReader findDestinationsResultReader(RedisSerializer destRedisSerializer) {
        return RedisElementReader.from(destRedisSerializer);
    }

}
