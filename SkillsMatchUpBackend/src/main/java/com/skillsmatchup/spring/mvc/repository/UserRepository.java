package com.skillsmatchup.spring.mvc.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.skillsmatchup.spring.mvc.model.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends  ReactiveMongoRepository<User, String>{ 

    @Query("{ 'name' : ?0 }")
    Mono<User> findByName(String name);

}