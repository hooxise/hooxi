package com.hooxi.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HooxiEventIngestionApplication {

  public static void main(String[] args) {
    SpringApplication.run(HooxiEventIngestionApplication.class, args);
  }
}
