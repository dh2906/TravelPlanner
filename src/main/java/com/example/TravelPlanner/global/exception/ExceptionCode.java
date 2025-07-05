package com.example.TravelPlanner.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "회원 정보를 찾을 수 없습니다."),
    PLAN_NOT_FOUND(404, "일정 정보를 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(409, "이미 사용 중인 이메일입니다."),
    LOGIN_FAILED(400, "이메일 또는 비밀번호가 올바르지 않습니다."),
    NO_ACCESS_TOKEN(401, "액세스 토큰이 존재하지 않습니다.");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
