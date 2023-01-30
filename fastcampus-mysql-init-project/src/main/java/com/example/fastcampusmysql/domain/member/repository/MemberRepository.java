package com.example.fastcampusmysql.domain.member.repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.fastcampusmysql.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

	static final String TABLE = "member";

	//     BeanPropertyRowMapper를 사용하면 별도 맵퍼가 필요 없지만, 기본생성자 + setter를 이용해서 만들기때문에 final 필드를 열어줘야함
	//    private final BeanPropertyRowMapper<Member> mapper = BeanPropertyRowMapper.newInstance(Member.class);
	private static final RowMapper<Member> ROW_MAPPER =
			(ResultSet resultSet, int rowNum) -> Member.builder()
					.id(resultSet.getLong("id"))
					.nickname(resultSet.getString("nickname"))
					.email(resultSet.getString("email"))
					.birthday(resultSet.getObject("birthday", LocalDate.class))
					.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
					.build();

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public Optional<Member> findById(Long id) {
		var params = new MapSqlParameterSource()
				.addValue("id", id);
		String query = String.format("SELECT * FROM `%s` WHERE id = :id", TABLE);
		List<Member> members = jdbcTemplate.query(query, params, ROW_MAPPER);

		Member nullableMember = DataAccessUtils.singleResult(members);
		return Optional.ofNullable(nullableMember);
	}

	public List<Member> findAllByIdIn(List<Long> ids) {
		var params = new MapSqlParameterSource()
				.addValue("ids", ids);
		String query = String.format("SELECT * FROM `%s` WHERE id in (:ids)", TABLE);
		return jdbcTemplate.query(query, params, ROW_MAPPER);
	}

	public Member save(Member member) {
		if (member.getId() == null)
			return insert(member);
		return update(member);
	}

	private Member insert(Member member) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
				.withTableName(TABLE)
				.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		var id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Member.builder()
				.id(id)
				.nickname(member.getNickname())
				.email(member.getEmail())
				.birthday(member.getBirthday())
				.build();
	}

	private Member update(Member member) {
		var params = new BeanPropertySqlParameterSource(member);
		var sql = String.format(
				"UPDATE `%s` set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
		jdbcTemplate.update(sql, params);
		return member;
	}
}
