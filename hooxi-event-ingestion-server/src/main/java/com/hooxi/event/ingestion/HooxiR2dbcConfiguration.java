package com.hooxi.event.ingestion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooxi.event.ingestion.data.repository.convertor.EventHeadersReadingConverter;
import com.hooxi.event.ingestion.data.repository.convertor.EventHeadersWritingConverter;
import io.r2dbc.spi.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

@Configuration
public class HooxiR2dbcConfiguration extends AbstractR2dbcConfiguration {

  @Autowired ObjectMapper mapper;

  @Override
  public ConnectionFactory connectionFactory() {
    return super.databaseClient().getConnectionFactory();
  }

  @Override
  public List<Object> getCustomConverters() {
    List<Object> converters = new ArrayList<>();
    converters.add(new EventHeadersWritingConverter(mapper));
    converters.add(new EventHeadersReadingConverter(mapper));
    return converters;
  }
}
