package com.example.fastcampusmysql.domain.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TimelineReadService {

	private final TimelineRepository timelineRepository;

	public PageCursor<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
		var timelines = findAllBy(memberId, cursorRequest);
		var nextKey = timelines.stream()
				.mapToLong(Timeline::getId)
				.min().orElse(CursorRequest.NONE_KEY);
		return new PageCursor<>(cursorRequest.next(nextKey), timelines);
	}

	private List<Timeline> findAllBy(Long memberId, CursorRequest cursorRequest) {
		if (cursorRequest.hasKey()) {
			return timelineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(
					cursorRequest.key(),
					memberId,
					cursorRequest.size()
			);
		}

		return timelineRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
	}
}
