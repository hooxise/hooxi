package com.hooxi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooxi.config.repository.redis.DestinationRedisSerializer;
import com.hooxi.data.model.dest.Destination;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.IOException;
import java.util.List;

@Configuration
public class HooxiTestConfig {
    @Bean
    public RedisTemplate<String, Destination> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                            ObjectMapper mapper) {
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new DestinationRedisSerializer(mapper));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisScript<List> script() throws IOException {

        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("findDestinations.lua"));
        try {
            return RedisScript.of(scriptSource.getScriptAsString(), List.class);
        } catch (IOException e) {
            throw e;
        }
    }
}
