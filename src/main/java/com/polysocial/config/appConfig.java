package com.polysocial.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.polysocial.dto.MemberGroupDTO;
import com.polysocial.dto.UserDTO;
import com.polysocial.entity.Groups;

@Configuration
public class appConfig {
    @Bean 
    public static LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lcf = new LettuceConnectionFactory();
        lcf.setHostName("localhost");
        lcf.setPort(6379);		
        lcf.afterPropertiesSet();
        return lcf;
    }

    public static RedisTemplate<Long, MemberGroupDTO> redisTemplate() {
        RedisTemplate<Long, MemberGroupDTO> redisTemplate = new RedisTemplate<Long ,MemberGroupDTO>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    public static RedisTemplate<Long, UserDTO> redisTemplateUserDTO() {
        RedisTemplate<Long, UserDTO> redisTemplate = new RedisTemplate<Long ,UserDTO>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
