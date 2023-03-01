package com.example.fastcampusmysql.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.PostCommand;
import com.example.fastcampusmysql.domain.post.PostWriteService;
import com.example.fastcampusmysql.domain.post.TimelineWriteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {

	private final PostWriteService postWriteService;
	private final FollowReadService followReadService;
	private final TimelineWriteService timelineWriteService;

	@Transactional
	public Long execute(PostCommand command) {
		var postId = postWriteService.create(command);

		var followerMemberIds = followReadService
				.getFollowers(command.memberId()).stream()
				.map((Follow::getFromMemberId)).toList();

		timelineWriteService.deliveryToTimeLine(postId, followerMemberIds);

		return postId;
	}
}
