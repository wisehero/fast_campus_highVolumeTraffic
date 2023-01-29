package com.example.fastcampusmysql.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

	private static final Long NAME_MAX_LENGTH = 10L;
	private final Long id;
	private final String email;
	private final LocalDate birthday;
	private final LocalDateTime createdAt;
	private String nickname;

	@Builder
	public Member(Long id, String nickname, String email, LocalDate birthday,
			LocalDateTime createdAt) {
		this.id = id;
		validateNickName(nickname);
		this.nickname = Objects.requireNonNull(nickname);
		this.email = Objects.requireNonNull(email);
		this.birthday = Objects.requireNonNull(birthday);
		this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
	}

	public void changeNickName(String to) {
		validateNickName(to);
		nickname = to;
	}

	private void validateNickName(String nickname) {
		Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
	}
}
