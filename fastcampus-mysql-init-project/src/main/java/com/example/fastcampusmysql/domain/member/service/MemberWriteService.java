package com.example.fastcampusmysql.domain.member.service;

import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

	private final MemberRepository memberRepository;

	public Member register(RegisterMemberCommand command) {
		/*
		목표 - 회원정보를 등록한다.
		파라미터 - memberRegisterCommand
		 */
		Member member = Member.builder()
				.email(command.email())
				.nickname(command.nickname())
				.birthday(command.birthday())
				.companyCode(command.companyCode())
				.build();

		return memberRepository.save(member);
	}

	public void changeNickname(Long memberId, String nickName) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		member.changeNickName(nickName);
		memberRepository.save(member);
	}
}
