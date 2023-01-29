package com.example.fastcampusmysql.domain.member.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

	@DisplayName("회원은 닉네임을 변경할 수 있다.")
	@Test
	public void testChangeNickName() {
		Member member = buildMember("chairman");
		var expected = "cat";

		member.changeNickName(expected);
		Assertions.assertEquals(expected, member.getNickname());
	}

	@DisplayName("회원 닉네임의 길이는 10자를 초과할 수 없다.")
	@Test
	public void testNicknameMaxLength() {
		Member member = buildMember("super");
		var overMaxLengthName = "superChairman";

		Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> member.changeNickName(overMaxLengthName)
		);
	}

	private Member buildMember(String nickname) {
		return Member.builder()
				.nickname(nickname)
				.email("pnu@fastcmapus.com")
				.birthday(LocalDate.now())
				.build();
	}
}
