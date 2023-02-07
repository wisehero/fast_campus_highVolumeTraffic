package helloredis.demo.RedisCaching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import helloredis.demo.RedisCaching.dto.UserProfile;
import helloredis.demo.RedisCaching.service.UserService;

@RestController
public class ApiController {

	@Autowired
	private UserService userService;

	@GetMapping("/users/{userId}/profile")
	public UserProfile getUserProfile(@PathVariable String userId) {
		return userService.getUserProfile(userId);
	}
}
