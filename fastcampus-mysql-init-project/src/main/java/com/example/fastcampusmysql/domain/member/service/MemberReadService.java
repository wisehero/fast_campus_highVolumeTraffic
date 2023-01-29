package com.example.fastcampusmysql.domain.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberReadService {

	private final MemberRepository memberRepository;

	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow();
	}

	public List<Member> getMembers(List<Long> memberIds) {
		return memberRepository.findAllByIdIn(memberIds);
	}
}
