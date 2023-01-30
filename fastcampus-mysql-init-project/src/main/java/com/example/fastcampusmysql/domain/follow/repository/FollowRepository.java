package com.example.fastcampusmysql.domain.follow.repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FollowRepository {

	static final String TABLE = "follow";
	private static final RowMapper<Follow> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Follow.builder()
			.id(resultSet.getLong("id"))
			.fromMemberId(resultSet.getLong("fromMemberId"))
			.toMemberId(resultSet.getLong("toMemberId"))
			.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
			.build();
	private final JdbcTemplate jdbcTemplate;

	public List<Follow> findAllByFromMemberId(Long fromMemberId) {
		String query = String.format("SELECT * FROM `%s` WHERE fromMemberId =?", TABLE);
		return jdbcTemplate.query(query, ROW_MAPPER, fromMemberId);
	}

	public Follow save(Follow follow) {
		if (follow.getId() == null)
			return insert(follow);

		throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다");
	}

	public Follow insert(Follow follow) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName(TABLE)
				.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
		var id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Follow.builder()
				.id(id)
				.fromMemberId(follow.getFromMemberId())
				.toMemberId(follow.getToMemberId())
				.createdAt(follow.getCreatedAt())
				.build();
	}
}