package com.example.fastcampusmysql.domain.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	public Page<Post> getPosts(Long memberId, PageRequest pageRequest) {
		return postRepository.findAllByMemberId(memberId, pageRequest);
	}
}
