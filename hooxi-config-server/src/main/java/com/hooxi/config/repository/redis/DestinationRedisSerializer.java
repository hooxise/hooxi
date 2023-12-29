package com.hooxi.config.repository.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hooxi.config.repository.entity.DestinationCacheEntity;
import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.json.deserialization.DestinationDeserializer;
import java.io.IOException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class DestinationRedisSerializer implements RedisSerializer {

  private ObjectMapper mapper;

  public DestinationRedisSerializer(ObjectMapper objectMapper) {
    this.mapper = objectMapper;
    SimpleModule simpleModule = new SimpleModule();
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    simpleModule.addDeserializer(Destination.class, new DestinationDeserializer());
    mapper.registerModule(simpleModule);
  }

  @Override
  public byte[] serialize(Object o) throws SerializationException {
    try {
      return mapper.writeValueAsBytes(o);
    } catch (JsonProcessingException e) {
      throw new SerializationException("failed to serlialize object", e);
    }
  }

  @Override
  public Object deserialize(byte[] bytes) throws SerializationException {
    try {
      return mapper.readValue(bytes, DestinationCacheEntity.class);
    } catch (IOException e) {
      throw new SerializationException("failed to deserlialize object", e);
    }
  }
}
