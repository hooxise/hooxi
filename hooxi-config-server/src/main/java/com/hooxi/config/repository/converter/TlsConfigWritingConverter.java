package com.hooxi.config.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooxi.data.model.dest.security.TLSConfig;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class TlsConfigWritingConverter implements Converter<TLSConfig, Json> {
  private static final Logger logger = LoggerFactory.getLogger(TlsConfigWritingConverter.class);
  private ObjectMapper mapper;

  @Autowired
  public TlsConfigWritingConverter(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public Json convert(TLSConfig value) {
    try {
      return Json.of(mapper.writeValueAsString(value));
    } catch (JsonProcessingException e) {
      logger.error("Error converting TLSConfig {} to json", value, e);
      return Json.of("");
    }
  }
}
