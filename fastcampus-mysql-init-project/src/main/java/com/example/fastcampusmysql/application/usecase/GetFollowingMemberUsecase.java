package com.example.fastcampusmysql.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetFollowingMemberUsecase {
	private final MemberReadService memberReadService;
	private final FollowReadService followReadService;

	public List<MemberDto> execute(Long memberId) {
		var follows = followReadService.getFollowings(memberId);
		var memberIds = follows
				.stream()
				.map(Follow::getToMemberId)
				.toList();
		return memberReadService.getMembers(memberIds);
	}
}
