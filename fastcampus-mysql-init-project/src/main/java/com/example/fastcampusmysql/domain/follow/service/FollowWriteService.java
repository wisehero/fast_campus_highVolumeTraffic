package com.example.fastcampusmysql.domain.follow.service;

import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowWriteService {

	private final FollowRepository followRepository;

	public Follow create(MemberDto fromMember, MemberDto toMember) {
		if (fromMember.id().equals(toMember.id())) { // 회원이 자신을 팔로우할 수는 없다.
			throw new IllegalArgumentException("From, to 회원이 동일합니다.");
		}

		var follow = Follow
				.builder()
				.fromMemberId(fromMember.id())
				.toMemberId(toMember.id())
				.build();

		return followRepository.save(follow);
	}
}
