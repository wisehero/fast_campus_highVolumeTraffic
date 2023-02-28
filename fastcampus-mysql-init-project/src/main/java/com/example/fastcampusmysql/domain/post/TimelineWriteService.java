package com.example.fastcampusmysql.domain.post;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {

	private final TimelineRepository timelineRepository;

	public void deliveryToTimeLine(Long postId, List<Long> toMemberIds) {
		var timeLines = toMemberIds.stream()
				.map((memberId) -> toTimeLine(postId, memberId))
				.toList();
		timelineRepository.bulkInsert(timeLines);
	}

	private Timeline toTimeLine(Long postId, Long memberId) {
		return Timeline.builder()
				.memberId(memberId)
				.postId(postId)
				.build();
	}
}
