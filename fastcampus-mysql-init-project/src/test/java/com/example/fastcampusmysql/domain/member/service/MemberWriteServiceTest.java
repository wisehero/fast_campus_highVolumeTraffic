package com.example.fastcampusmysql.domain.member.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberWriteServiceTest {

	@Autowired
	private MemberWriteService service;

	@Autowired
	private MemberRepository repository;

	@DisplayName("회원정보 등록 테스트")
	@Test
	public void testRegister() {
		var command = new RegisterMemberCommand(
				"wisehero@naver.com",
				"kjw",
				LocalDate.now(),
				1L
		);

		var member = service.register(command);

		assertEquals(command, member);
	}

	@DisplayName("회원정보 이름 변경 테스트")
	@Test
	public void testChangeName() {
		Member saved = saveMember();
		var expected = "chair";

		service.changeNickname(saved.getId(), expected);

		var result = repository.findById(saved.getId()).orElseThrow();
		Assertions.assertEquals(expected, result.getNickname());
	}

	private Member saveMember() {
		var member = MemberFixtureFactory.create();
		return repository.save(member);
	}

	private void assertEquals(RegisterMemberCommand command, Member member) {
		Assertions.assertNotNull(member.getId());
		Assertions.assertNotNull(member.getCreatedAt());

		Assertions.assertEquals(command.email(), member.getEmail());
		Assertions.assertEquals(command.nickname(), member.getNickname());
		Assertions.assertEquals(command.birthday(), member.getBirthday());
	}

}
