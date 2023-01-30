package com.example.fastcampusmysql.domain.post;

public record PostCommand(Long memberId, String contents) {
}
