package com.hooxi.config.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooxi.data.model.dest.security.AuthenticationConfig;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class AuthConfigReadingConverter implements Converter<Json, AuthenticationConfig> {

  private static final Logger logger = LoggerFactory.getLogger(AuthConfigReadingConverter.class);
  private ObjectMapper mapper;

  @Autowired
  public AuthConfigReadingConverter(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public AuthenticationConfig convert(Json source) {
    try {
      return mapper.readValue(source.asString(), AuthenticationConfig.class);
    } catch (JsonProcessingException e) {
      logger.error("Error converting json {} to AuthenticationConfig", source.asString(), e);
      return null;
    }
  }
}
