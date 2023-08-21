package com.hooxi.config.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooxi.data.model.dest.Destination;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class DestinationReadingConverter implements Converter<Json, Destination> {
    private static final Logger logger = LoggerFactory.getLogger(DestinationReadingConverter.class);
    private ObjectMapper mapper;

    @Autowired
    public DestinationReadingConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Destination convert(Json source) {
        try {
            return mapper.readValue(source.asString(), Destination.class);
        } catch (JsonProcessingException e) {
            logger.error("Error converting json {} to Destination", source.asString(), e);
            return null;
        }
    }
}
