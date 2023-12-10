package com.skillsmatchup.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.skillsmatchup.spring.mvc.model.User;
import com.skillsmatchup.spring.mvc.service.UserService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("user")
@AllArgsConstructor
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public Flux<User> getAll() {
		System.out.println("All the user information");
		return userService.getAll();
	}

	@GetMapping("{name}")
	public Mono<User> getById(@PathVariable("name") final String name) {
		System.out.println("One user information based for the given name");
		return userService.getByName(name);
	}

	@PutMapping("{id}")
	public Mono updateById(@PathVariable("id") final String id, @RequestBody final User user) {
		System.out.println("Updating an user Info");
		return userService.update(id, user);
	}

	@PostMapping("login")
    public Mono<User> login(@RequestBody final User user) {
        System.out.println("Login attempt for user: " + user.getEmail());

		Mono<User> tempUserMono = userService.getByEmailAndPassword(user.getEmail(), user.getPassword()).defaultIfEmpty(new User());

		return tempUserMono.flatMap(data -> {
			if (data.getId() != null) {
				return userService.getByEmailAndPassword(user.getEmail(), user.getPassword());
			} else {
				String errorMessage = "Invalid input. Please check the data.";
				return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage));
			}
		});
    }

	@PostMapping
	public Mono save(@RequestBody final User user) {
		System.out.println("Added user Info " + user.getEmail() + " - " + user.getPassword());
		return userService.save(user);
	}

	@DeleteMapping("{id}")
	public Mono delete(@PathVariable final String id) {
		System.out.println("An user Info deleted");
		return userService.delete(id);
	}
}