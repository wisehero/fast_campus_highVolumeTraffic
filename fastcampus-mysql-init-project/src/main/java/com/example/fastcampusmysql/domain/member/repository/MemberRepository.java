package com.example.fastcampusmysql.domain.member.repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.fastcampusmysql.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

	static final String TABLE = "MEMBER";
	private static final RowMapper<Member> ROW_MAPPER =
			(ResultSet resultSet, int rowNum) -> Member.builder()
					.id(resultSet.getLong("id"))
					.nickname(resultSet.getString("nickname"))
					.email(resultSet.getString("email"))
					.birthday(resultSet.getObject("birthday", LocalDate.class))
					.companyCode(resultSet.getLong("companyCode"))
					.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
					.build();

	private final JdbcTemplate jdbcTemplate;

	public Optional<Member> findById(Long id) {
		String query = String.format("SELECT * FROM `%s` WHERE id = ?", TABLE);
		List<Member> members = jdbcTemplate.query(query, ROW_MAPPER, id);

		Member nullableMember = DataAccessUtils.singleResult(members);
		return Optional.ofNullable(nullableMember);
	}

	public Member save(Member member) {
		if (member.getId() == null)
			return insert(member);
		return update(member);
	}

	private Member insert(Member member) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName(TABLE)
				.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		var id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Member.builder()
				.id(id)
				.nickname(member.getNickname())
				.email(member.getEmail())
				.birthday(member.getBirthday())
				.companyCode(member.getCompanyCode())
				.build();
	}

	private Member update(Member member) {
		var sql = String.format("UPDATE `%s` set email = ?, nickname = ?, birthday = ?, companyCode = ? WHERE id = ?",
				TABLE);
		jdbcTemplate.update(sql, member.getEmail(), member.getNickname(), member.getBirthday(), member.getCompanyCode(),
				member.getId());
		return member;
	}
}
