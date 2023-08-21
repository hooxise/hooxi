package com.hooxi.event.ingestion;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;

public class CassandraTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        GenericContainer<?> cassandra =
                new GenericContainer<>("cassandra:3").withExposedPorts(9042);

        cassandra.start();
        System.out.println("cassandra.getContainerIpAddress() " + cassandra.getContainerIpAddress());
        System.out.println("cassandra.getMappedPort() " + cassandra.getMappedPort(9042));
        TestPropertyValues.of(
                "spring.data.cassandra.contact-points=" + cassandra.getContainerIpAddress(),
                "spring.data.cassandra.port=" + cassandra.getMappedPort(9042)
        ).applyTo(applicationContext);
    }
}

