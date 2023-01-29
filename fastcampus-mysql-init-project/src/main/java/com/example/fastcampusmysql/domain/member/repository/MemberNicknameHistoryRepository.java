package com.example.fastcampusmysql.domain.member.repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberNicknameHistoryRepository {

	static final String TABLE = "MemberNicknameHistory";
	private static final RowMapper<MemberNicknameHistory> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> MemberNicknameHistory.builder()
			.id(resultSet.getLong("id"))
			.memberId(resultSet.getLong("memberId"))
			.nickname(resultSet.getString("nickname"))
			.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
			.build();
	private final JdbcTemplate jdbcTemplate;

	public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
		String query = String.format("SELECT * FROM `%s` WHERE memberId = ?", TABLE);
		return jdbcTemplate.query(query, ROW_MAPPER, memberId);
	}

	public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory) {
		if (memberNicknameHistory.getId() == null) {
			return insert(memberNicknameHistory);
		}

		throw new UnsupportedOperationException("MemberNameHistory는 갱신을 지원하지 않습니다.");
	}

	private MemberNicknameHistory insert(MemberNicknameHistory member) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName(TABLE)
				.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		var id = jdbcInsert.executeAndReturnKey(params).longValue();

		return MemberNicknameHistory.builder()
				.id(id)
				.memberId(member.getMemberId())
				.nickname(member.getNickname())
				.createdAt(member.getCreatedAt())
				.build();
	}

}
