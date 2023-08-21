package com.hooxi.config;

import com.hooxi.config.repository.entity.DestinationCacheEntity;
import com.hooxi.config.repository.redis.DestinationRedisRepository;
import com.hooxi.config.router.handler.ConfigServiceHandler;
import com.hooxi.data.model.dest.Destination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootTest
class HooxiConfigServiceApplicationTests {

    @Autowired
    DestinationRedisRepository destinationRedisRepository;

    @Autowired
    @Qualifier("destinationMappingRoute")
    RouterFunction<ServerResponse> routerFunction;

    @Autowired
    ConfigServiceHandler configServiceHandler;

    @Autowired
    ReactiveRedisOperations<String, DestinationCacheEntity> template;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    RedisTemplate<String, Destination> redisTemplate;

    @BeforeEach
    public void setup() {

    }

    @Test
    void testGetConfig() throws Exception {
        WebTestClient wte = WebTestClient.bindToRouterFunction(routerFunction).build();
        wte.get()
            .uri("/config/tenant/tenant1/domain/domain1/subdomain/subdomain1/eventtype/eventType1")
            .exchange()
            .expectBody()
            .consumeWith(response -> System.out.println(String.format("%d %s", response.getStatus().value(),
                new String(response.getResponseBodyContent()))));
    }

}
