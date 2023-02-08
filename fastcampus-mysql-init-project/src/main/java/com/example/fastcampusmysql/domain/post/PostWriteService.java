package com.example.fastcampusmysql.domain.post;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostWriteService {

	private final PostRepository postRepository;

	public Long create(PostCommand command) {
		var post = Post
				.builder()
				.memberId(command.memberId())
				.contents(command.contents())
				.build();

		return postRepository.save(post).getId();
	}

	public void bulkCreate() {

	}
}
