package com.example.fastcampusmysql.domain.post;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostReadService {

	private final PostRepository postRepository;

	public List<Post> getPosts(Long memberId) {
		return postRepository.findByMemberId(memberId);
	}

	public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
		return postRepository.groupByCreatedDate(request);
	}

	public List<Post> getPosts(Long memberId, LocalDate date) {
		return List.of();
	}
}
