package com.redis.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.redis.Entity.Person;

@Configuration
public class RedisConfiguration {
	
	@Value("${spring.redis.host}")
	private String HOSTNAME;
	
	@Value("${spring.redis.port}")
	private int PORT;
	
	@Value("${spring.redis.jedis.pool.max-active}")
	private int MAX_TOTAL;
	
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int MAX_IDLE;
	
	@Value("${spring.redis.jedis.pool.min-idle}")
	private int MIN_IDLE;
	
	
	/*@Bean
	public JedisClientConfiguration getJedisClientConfiguration() {
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder = 
				(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
		
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxTotal(MAX_TOTAL);
		genericObjectPoolConfig.setMaxIdle(MAX_IDLE);
		genericObjectPoolConfig.setMinIdle(MIN_IDLE);
		return JedisPoolingClientConfigurationBuilder.poolConfig(genericObjectPoolConfig).build();
	}*/
	
	
	
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(HOSTNAME);
        redisStandaloneConfiguration.setPort(PORT);
        //redisStandaloneConfiguration.redisStandaloneConfiguration.setDatabase(0);

        JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        //jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));// 60s connection timeout

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        return jedisConFactory;
    }
	
	@Bean
	public RedisTemplate<String, ?> redisTemplate() {
		RedisTemplate<String, ?> redisTemp = new RedisTemplate<String, Object>();
		redisTemp.setConnectionFactory(jedisConnectionFactory());
		redisTemp.setKeySerializer(new StringRedisSerializer());
		//redisTemp.setKeySerializer(new ());
		return redisTemp;
	}
	
	@Bean
	@Qualifier("listOperation")
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate){
		return redisTemplate.opsForList();
	}
	
	@Bean
	@Qualifier("setOperation")
	public SetOperations<String, Person> setOperations(RedisTemplate<String, Person> redisTemplate){
		return redisTemplate.opsForSet();
	}
	
}
