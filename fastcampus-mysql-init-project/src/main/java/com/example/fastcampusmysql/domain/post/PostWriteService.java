package com.example.fastcampusmysql.domain.post;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostWriteService {

	private final PostRepository postRepository;

	public void create(PostCommand command) {
		var post = Post
				.builder()
				.memberId(command.memberId())
				.contents(command.contents())
				.build();

		postRepository.save(post);
	}

	public void bulkCreate() {

	}
}
