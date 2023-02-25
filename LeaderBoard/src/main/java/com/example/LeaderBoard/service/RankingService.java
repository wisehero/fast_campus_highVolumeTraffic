package com.example.LeaderBoard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {

	private static final String LEADERBOARD_KEY = "leaderBoard";
	private final StringRedisTemplate redisTemplate;


	public boolean setUserScore(String userId, int score) {
		ZSetOperations zSetOps = redisTemplate.opsForZSet();
		zSetOps.add(LEADERBOARD_KEY, userId, score);

		return true;
	}

	public Long getUserRanking(String userId) {
		ZSetOperations zSetOps = redisTemplate.opsForZSet();

		return zSetOps.reverseRank(LEADERBOARD_KEY, userId);
	}

	public List<String> getTopRank(int limit) {
		ZSetOperations zSetOps = redisTemplate.opsForZSet();
		Set rangeSet = zSetOps.reverseRange(LEADERBOARD_KEY, 0, limit - 1);

		return new ArrayList<>(rangeSet);
	}
}
