package com.example.fastcampusmysql.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

	private final MemberRepository memberRepository;
	private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

	@Transactional
	public Member create(RegisterMemberCommand command) {
		/*
		목표 - 회원정보를 등록한다.
		파라미터 - memberRegisterCommand
		 */
		Member member = Member.builder()
				.email(command.email())
				.nickname(command.nickname())
				.birthday(command.birthday())
				.build();

		var savedMember = memberRepository.save(member);
		savedMemberNickNameHistory(savedMember);
		return savedMember;
	}

	private void savedMemberNickNameHistory(Member member) {
		var history = MemberNicknameHistory
				.builder()
				.memberId(member.getId())
				.nickname(member.getNickname())
				.build();

		memberNicknameHistoryRepository.save(history);
	}

	@Transactional
	public void changeNickname(Long memberId, String nickName) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		member.changeNickName(nickName);
		memberRepository.save(member);
	}
}
