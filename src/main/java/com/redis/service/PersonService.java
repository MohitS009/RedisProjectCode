package com.redis.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redis.Entity.Person;
import com.redis.repository.PersonRepo;
import com.redis.repository.RedisDataRepository;

@Service
public class PersonService {
	
	@Autowired
	private RedisDataRepository redisDataRepository; 
	
	@Autowired
	private PersonRepo personRepo;
	
	public void savePerson(Person personObj) {
		personRepo.save(personObj);
		redisDataRepository.saveDataIntoRedis(personObj.getFirstName()+personObj.getLastName(), personObj);
	}
	
	public Set<Person> fetchPersonDetails(String key){
		Set<Person> setOfPersonInfo = new HashSet<>();
		if(!key.equalsIgnoreCase("") && key!= null) {
			setOfPersonInfo =redisDataRepository.fetchDataFromRedisServer(key);
		}else {
			System.out.println("key cannot be null");
		}
		return setOfPersonInfo;
	}
	
	public Long deleteKeysFromRedis(List<String> keys) {
		return redisDataRepository.delKeysFromRedis(keys);
	}
	
	public Set<String> fetchAllKeysFromRedis() {
		return redisDataRepository.fetchAllKeysFromRedis();
	}
	
	public Boolean setExpirationTimeForKey(String key, Long timeOut) {
		return redisDataRepository.setExpirationTime(key, timeOut);
	}
}	
