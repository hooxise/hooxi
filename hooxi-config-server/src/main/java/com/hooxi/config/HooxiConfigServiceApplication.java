package com.hooxi.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HooxiConfigServiceApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        SpringApplication.run(HooxiConfigServiceApplication.class, args);
  }
}
