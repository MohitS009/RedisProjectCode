package com.redis.repository;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.redis.Entity.Person;

@Repository
public interface RedisDataRepository {
	
	public Long saveDataIntoRedis(String key, Person perosonObj);
	
	public Set<Person> fetchDataFromRedisServer(String key);
	
	public Long delKeysFromRedis(List<String> key);
	
	public Set<String> fetchAllKeysFromRedis();
	
	public Boolean setExpirationTime(String key, Long milliseconds);

}
