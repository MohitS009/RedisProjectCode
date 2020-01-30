package com.redis.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.Entity.Person;
import com.redis.service.PersonService;

@RestController
@RequestMapping("/person")
public class RedisClientAppTestController {

	@Autowired
	private PersonService personService;
	
	@PostMapping("/saveInfo")
	public void savePersonInfo(@RequestBody Person personObject) {
		if(personObject != null) {
			personService.savePerson(personObject);
		}else {
			System.out.println("Person Object cannot be null..");
		}
	}
	
	@GetMapping("/fetchPersonInfo/{key}")
	public Set<Person> fetchPersonInfo(@PathVariable("key") String key){
		Set<Person> setOfPersonObj = personService.fetchPersonDetails(key);
		System.out.println("setOfPersonObj  :: "+setOfPersonObj);
				
		for (Person object : setOfPersonObj) {
			System.out.println(object.getId());
			System.out.println(object.getFirstName());
			System.out.println(object.getLastName());
			System.out.println(object.getAddress());
			
		}
		return setOfPersonObj;
	}
	
	@PostMapping("/deleteKeys")
	public Long deleteKey(@RequestBody List<String> keys){
		Long deleteKeys = personService.deleteKeysFromRedis(keys);
		return deleteKeys;
	}
	
	@GetMapping("/getAllKeys")
	public Set<String> getAllKeys(){
		Set<String> allKeys = personService.fetchAllKeysFromRedis();
		return allKeys;
	}
	
	@PostMapping("/setExpireTimeForKey")
	public Boolean ttlForKey(@RequestBody Map<?,?> ttlParams) {
		return personService.setExpirationTimeForKey(ttlParams.get("key").toString(), Long.valueOf(ttlParams.get("timeOut").toString()));
	}
}
