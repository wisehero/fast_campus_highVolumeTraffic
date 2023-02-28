package com.example.fastcampusmysql.domain.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostReadService {

	private final PostRepository postRepository;

	public List<Post> getPosts(Long memberId) {
		return postRepository.findByMemberId(memberId);
	}

	public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
		return postRepository.groupByCreatedDate(request);
	}

	public List<Post> getPosts(List<Long> postIds) {
		return postRepository.findAllByIdIn(postIds);
	}

	public Page<Post> getPosts(Long memberId, PageRequest pageRequest) {
		return postRepository.findAllByMemberId(memberId, pageRequest);
	}

	public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
		var posts = findAllBy(memberIds, cursorRequest);
		long nextKey = getNextKey(posts);
		return new PageCursor<Post>(cursorRequest.next(nextKey), posts);
	}

	public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
		var posts = findAllBy(memberId, cursorRequest);

		var nextKey = posts.stream()
				.mapToLong(Post::getId)
				.min()
				.orElseGet(() -> CursorRequest.NONE_KEY);

		return new PageCursor<Post>(cursorRequest.next(nextKey), posts);
	}

	private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
		if (cursorRequest.hasKey()) {
			return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(
					cursorRequest.key(),
					memberId,
					cursorRequest.size()
			);
		}

		return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
	}

	private long getNextKey(List<Post> posts) {
		return posts.stream()
				.mapToLong(Post::getId)
				.min()
				.orElseGet(() -> CursorRequest.NONE_KEY);
	}

	private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
		if (cursorRequest.hasKey()) {
			return postRepository.findAllByLessThanIdAndMemberIdInAndOrderByIdDesc(
					cursorRequest.key(),
					memberIds,
					cursorRequest.size()
			);
		}

		return postRepository.findAllByMemberIdInAndOrderByIdDesc(memberIds, cursorRequest.size());
	}

}
