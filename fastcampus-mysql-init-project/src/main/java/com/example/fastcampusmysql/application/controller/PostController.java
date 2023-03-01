package com.example.fastcampusmysql.application.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastcampusmysql.application.usecase.CreatePostUsecase;
import com.example.fastcampusmysql.application.usecase.GetTimelinePostsUsecase;
import com.example.fastcampusmysql.domain.post.DailyPostCount;
import com.example.fastcampusmysql.domain.post.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.Post;
import com.example.fastcampusmysql.domain.post.PostCommand;
import com.example.fastcampusmysql.domain.post.PostReadService;
import com.example.fastcampusmysql.domain.post.PostWriteService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostWriteService postWriteService;
	private final PostReadService postReadService;

	private final GetTimelinePostsUsecase getTimelinePostsUsecase;
	private final CreatePostUsecase createPostUsecase;

	@PostMapping
	public Long create(@RequestBody PostCommand command) {
		// return postWriteService.create(command);
		return createPostUsecase.execute(command);
	}

	@GetMapping("/daily-post-counts")
	public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
		return postReadService.getDailyPostCount(request);
	}

	@GetMapping("/members/{memberId}")
	public Page<Post> getPosts(
			@PathVariable Long memberId,
			@RequestParam Integer page,
			@RequestParam Integer size
	) {
		return postReadService.getPosts(memberId, PageRequest.of(page, size));
	}

	@GetMapping("/members/{memberId}/timeline")
	public PageCursor<Post> getTimeline(
			@PathVariable Long memberId,
			CursorRequest cursorRequest
	) {
		//        return getTimelinePostsUsecase.execute(memberId, cursorRequest);
		return getTimelinePostsUsecase.executeByTimeline(memberId, cursorRequest);
	}

	@PostMapping("/posts/{postId}/like")
	public void likePost(@PathVariable Long postId) {
		postWriteService.likePost(postId);
	}
}
