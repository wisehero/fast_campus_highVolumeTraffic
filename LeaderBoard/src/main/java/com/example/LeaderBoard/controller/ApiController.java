package com.example.LeaderBoard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.LeaderBoard.service.RankingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {

	private final RankingService rankingService;

	@GetMapping("/setScore")
	public Boolean setScore(@RequestParam String userId, @RequestParam int score) {
		return rankingService.setUserScore(userId, score);
	}

	@GetMapping("/getRank")
	public Long getUserRank(@RequestParam String userId) {
		return rankingService.getUserRanking(userId);
	}

	@GetMapping("/getTopRanks")
	public List<String> getTopRanks() {
		return rankingService.getTopRank(3);
	}
}
