package com.hooxi.config.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooxi.data.model.dest.Destination;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class DestinationWritingConverter implements Converter<Destination, Json> {
    private static final Logger logger = LoggerFactory.getLogger(DestinationWritingConverter.class);
    private ObjectMapper mapper;

    @Autowired
    public DestinationWritingConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Json convert(Destination value) {
        try {
            return Json.of(mapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            logger.error("Error converting destination {} to json", value, e);
            return Json.of("");
        }
    }
}
