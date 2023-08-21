package com.hooxi.event.ingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@SpringBootApplication
public class HooxiEventIngestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HooxiEventIngestionApplication.class, args);
	}

}
