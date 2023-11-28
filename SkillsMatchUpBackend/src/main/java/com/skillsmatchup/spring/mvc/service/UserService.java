package com.skillsmatchup.spring.mvc.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillsmatchup.spring.mvc.model.User;
import com.skillsmatchup.spring.mvc.repository.UserRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Flux<User> getAll() {
		return userRepository.findAll().switchIfEmpty(Flux.empty());
	}

	public Mono<User> getById(final String id) {
		return userRepository.findById(id);
	}

	public Mono update(final String id, final User user) {
		return userRepository.findById(id).flatMap(existingUser -> {
			existingUser.setEmail(user.getEmail());
			return userRepository.save(existingUser);
		});
	}

	public Mono save(final User user) {
		return userRepository.save(user);
	}

	public Mono delete(final String id) {
		final Mono<User> dbUser = getById(id);
		if (Objects.isNull(dbUser)) {
			return Mono.empty();
		}

		return getById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
				.flatMap(userToBeDeleted -> userRepository.delete(userToBeDeleted).then(Mono.just(userToBeDeleted)));
	}
}