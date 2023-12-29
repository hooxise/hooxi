package com.hooxi.event.ingestion;

import com.hooxi.event.HooxiEventIngestionApplication;
import com.hooxi.event.ingestion.data.repository.HooxiEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({HooxiEventIngestionApplication.class, HooxiConfiguration.class}) //
public class HooxiGrpcTestConfig {

  @Autowired HooxiEventRepository hooxiEventRepository;

  /* @Bean
  HooxiGrpcService hooxiGrpcService() {
      HooxiGrpcService service = new HooxiGrpcService();
      return service;
  }*/
}
