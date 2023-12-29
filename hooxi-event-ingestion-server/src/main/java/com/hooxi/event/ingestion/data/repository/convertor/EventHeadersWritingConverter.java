package com.hooxi.event.ingestion.data.repository.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class EventHeadersWritingConverter implements Converter<Map<String, String>, Json> {
  private static final Logger logger = LoggerFactory.getLogger(EventHeadersWritingConverter.class);
  private ObjectMapper mapper;

  @Autowired
  public EventHeadersWritingConverter(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public Json convert(Map<String, String> value) {
    try {
      return Json.of(mapper.writeValueAsString(value));
    } catch (JsonProcessingException e) {
      logger.error("Error converting headers {} to json", value, e);
      return Json.of("");
    }
  }
}
