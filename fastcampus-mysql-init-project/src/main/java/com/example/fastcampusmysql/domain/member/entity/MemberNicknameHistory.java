package com.example.fastcampusmysql.domain.member.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberNicknameHistory {

	private final Long id;
	private final Long memberId;
	private final LocalDateTime createdAt;
	private String nickname;

	@Builder
	public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
		this.id = id;
		this.memberId = Objects.requireNonNull(memberId);
		this.nickname = Objects.requireNonNull(nickname);
		this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
	}
}
