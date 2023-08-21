package com.hooxi.config.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooxi.data.model.dest.security.TLSConfig;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class TlsConfigReadingConverter implements Converter<Json, TLSConfig> {

    private static final Logger logger = LoggerFactory.getLogger(TlsConfigReadingConverter.class);
    private ObjectMapper mapper;

    @Autowired
    public TlsConfigReadingConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TLSConfig convert(Json source) {
        try {
            return mapper.readValue(source.asString(), TLSConfig.class);
        } catch (JsonProcessingException e) {
            logger.error("Error converting json {} to TLSConfig", source.asString(), e);
            return null;
        }
    }
}
