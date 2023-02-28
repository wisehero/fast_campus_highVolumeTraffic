package com.example.fastcampusmysql.application.usecase;

import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.Post;
import com.example.fastcampusmysql.domain.post.PostReadService;
import com.example.fastcampusmysql.domain.post.Timeline;
import com.example.fastcampusmysql.domain.post.TimelineReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetTimelinePostsUsecase {

	private final FollowReadService followReadService;
	private final PostReadService postReadService;
	private final TimelineReadService timelineReadService;

	public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
		var follows = followReadService.getFollowings(memberId);
		var followerMemberIds = follows
				.stream()
				.map(Follow::getToMemberId)
				.toList();

		return postReadService.getPosts(followerMemberIds, cursorRequest);
	}

	public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
		var pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
		var postIds = pagedTimelines.body().stream().map(Timeline::getPostId).toList();
		var posts = postReadService.getPosts(postIds);
		return new PageCursor<>(pagedTimelines.nextCursorRequest(), posts);
	}
}
