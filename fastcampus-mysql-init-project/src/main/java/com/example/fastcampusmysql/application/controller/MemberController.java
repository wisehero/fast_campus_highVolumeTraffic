package com.example.fastcampusmysql.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberWriteService memberWriteService;
	private final MemberReadService memberReadService;

	@PostMapping("")
	public MemberDto register(@RequestBody RegisterMemberCommand command) {
		var member = memberWriteService.create(command);
		return memberReadService.toDto(member);
	}

	@GetMapping("/{id}")
	public MemberDto getMember(@PathVariable Long id) {
		return memberReadService.getMember(id);
	}

	@PostMapping("/{id}/name")
	public MemberDto changeNickname(
			@PathVariable Long id,
			@RequestBody String name) {
		memberWriteService.changeNickname(id, name);
		return memberReadService.getMember(id);
	}

	@GetMapping("/{id}/name-histories")
	public List<MemberNicknameHistoryDto> getMemberNameHistories(@PathVariable Long id) {
		return memberReadService.getNicknameHistories(id);
	}
}
