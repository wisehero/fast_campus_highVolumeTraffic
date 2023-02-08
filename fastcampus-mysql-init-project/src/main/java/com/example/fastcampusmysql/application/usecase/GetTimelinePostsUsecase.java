package com.example.fastcampusmysql.application.usecase;

import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.Post;
import com.example.fastcampusmysql.domain.post.PostReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetTimelinePostsUsecase {

	private final FollowReadService followReadService;
	private final PostReadService postReadServices;

	public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
		var follows = followReadService.getFollowings(memberId);
		var followerMemberIds = follows
				.stream()
				.map(Follow::getToMemberId)
				.toList();

		return postReadServices.getPosts(followerMemberIds, cursorRequest);
	}
}
