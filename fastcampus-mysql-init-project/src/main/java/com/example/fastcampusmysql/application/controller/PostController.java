package com.example.fastcampusmysql.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastcampusmysql.domain.post.DailyPostCount;
import com.example.fastcampusmysql.domain.post.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.PostCommand;
import com.example.fastcampusmysql.domain.post.PostReadService;
import com.example.fastcampusmysql.domain.post.PostWriteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostWriteService postWriteService;
	private final PostReadService postReadService;

	@PostMapping
	public Long create(@RequestBody PostCommand command) {
		return postWriteService.create(command);
	}

	@GetMapping("/daily-post-counts")
	public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
		return postReadService.getDailyPostCount(request);
	}
}
