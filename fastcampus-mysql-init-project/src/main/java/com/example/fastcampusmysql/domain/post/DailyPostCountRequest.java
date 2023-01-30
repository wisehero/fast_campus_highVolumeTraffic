package com.example.fastcampusmysql.domain.post;

import java.time.LocalDate;

public record DailyPostCountRequest(Long memberId, LocalDate firstDate, LocalDate lastDate) {
}
