package com.demo.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.demo.redis.entity.Item;

@SpringBootApplication
public class RedisApplication {

	
	
	  @Bean
	  JedisConnectionFactory jedisConnectionFactory(){
	    return new JedisConnectionFactory();
	  }

	  @Bean
	  RedisTemplate<String, Item> redisTemplate(){
	    RedisTemplate<String,Item> redisTemplate = new RedisTemplate<String, Item>();
	    redisTemplate.setConnectionFactory(jedisConnectionFactory());
	    return redisTemplate;
	  }
	
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}
