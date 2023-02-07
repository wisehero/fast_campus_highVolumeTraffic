package helloredis.demo.RedisCaching.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import helloredis.demo.RedisCaching.dto.UserProfile;

@Service
public class UserService {

	@Autowired
	StringRedisTemplate redisTemplate;
	@Autowired
	private ExternalApiService externalApiService;

	public UserProfile getUserProfile(String userId) {
		String userName = null;

		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		String cachedName = ops.get("nameKey:" + userId);
		if (cachedName != null) {
			userName = cachedName;
		} else {
			userName = externalApiService.getUsername(userId);
			ops.set("nameKey:" + userId, userName, 5, TimeUnit.SECONDS);
		}

		int userAge = externalApiService.getUserAge(userId);

		return new UserProfile(userName, userAge);
	}
}
