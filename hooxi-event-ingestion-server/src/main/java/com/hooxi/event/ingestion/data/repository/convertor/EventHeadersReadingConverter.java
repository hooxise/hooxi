package com.hooxi.event.ingestion.data.repository.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class EventHeadersReadingConverter implements Converter<Json, Map<String, String>> {
  private static final Logger logger = LoggerFactory.getLogger(EventHeadersReadingConverter.class);
  private ObjectMapper mapper;

  @Autowired
  public EventHeadersReadingConverter(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public Map<String, String> convert(Json source) {
    try {
      return mapper.readValue(source.asString(), new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      logger.error("Error converting json {} to map", source.asString(), e);
      return null;
    }
  }
}
