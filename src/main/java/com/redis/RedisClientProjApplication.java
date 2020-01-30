package com.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.redis")
public class RedisClientProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisClientProjApplication.class, args);
	}

}
