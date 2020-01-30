package com.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redis.Entity.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person,Long>{

	//void save(Person person);
}
