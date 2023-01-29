package com.example.fastcampusmysql.domain.follow.service;

import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowWriteService {

	private final FollowRepository followRepository;

	public Follow create(Member fromMember, Member toMember) {
		if (fromMember.getId().equals(toMember.getId())) { // 회원이 자신을 팔로우할 수는 없다.
			throw new IllegalArgumentException("From, to 회원이 동일합니다.");
		}

		var follow = Follow
				.builder()
				.fromMemberId(fromMember.getId())
				.toMemberId(toMember.getId())
				.build();

		return followRepository.save(follow);
	}
}
