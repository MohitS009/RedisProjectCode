package com.redis.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import com.redis.Entity.Person;
import com.redis.repository.RedisDataRepository;

@Service
public class RedisRepositoryImpl implements RedisDataRepository{
	@Autowired
	@Qualifier("setOperation")
	private SetOperations<String, Person> setOperation;
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	@Override
	public Long saveDataIntoRedis(String key,Person personObject) {
		return setOperation.add(key, personObject);
	}

	@Override
	public Set<Person> fetchDataFromRedisServer(String key) {
		Set<Person> fetchedDataSet = setOperation.members(key);
		return fetchedDataSet;
	}

	@Override
	public Long delKeysFromRedis(List<String> key) {
		Long noOfKeyDelete = redisTemplate.delete(key);
		return noOfKeyDelete;
	}

	@Override
	public Set<String> fetchAllKeysFromRedis() {
		return redisTemplate.keys("*");
	}
	
	@Override
	public Boolean setExpirationTime(String key, Long timeOut) {
		return redisTemplate.expire(key, timeOut, TimeUnit.MILLISECONDS);
	}

}
