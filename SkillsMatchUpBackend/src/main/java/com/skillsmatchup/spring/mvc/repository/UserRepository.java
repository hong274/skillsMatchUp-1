package com.skillsmatchup.spring.mvc.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.skillsmatchup.spring.mvc.model.User;

@Repository
public interface UserRepository extends  ReactiveMongoRepository<User, String>{ 

}