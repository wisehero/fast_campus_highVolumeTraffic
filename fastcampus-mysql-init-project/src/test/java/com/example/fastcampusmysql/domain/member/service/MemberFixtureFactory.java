package com.example.fastcampusmysql.domain.member.service;

import static org.jeasy.random.FieldPredicates.*;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import com.example.fastcampusmysql.domain.member.entity.Member;

public class MemberFixtureFactory {
	public static Member create() {
		var parameter = new EasyRandomParameters()
				.excludeField(named("id"))
				.stringLengthRange(1, 10)
				.randomize(Long.class, new LongRangeRandomizer(1L, 100L));
		return new EasyRandom(parameter).nextObject(Member.class);
	}
}
