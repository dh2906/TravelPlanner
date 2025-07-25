package com.example.TravelPlanner.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    DUPLICATE_EMAIL(409, "이미 사용 중인 이메일입니다."),
    MEMBER_NOT_FOUND(404, "회원 정보를 찾을 수 없습니다."),
    PLAN_NOT_FOUND(404, "일정 정보를 찾을 수 없습니다."),
    PLAN_DETAIL_NOT_FOUND(404, "상세 일정을 찾을 수 없습니다."),
    PLAN_DETAIL_TIME_CONFLICT(400, "상세 일정의 시간이 겹칩니다."),
    INVALID_DAY_NUMBER(400, "여행 일차가 여행 기간을 벗어났습니다."),
    CHECKLIST_ITEM_NOT_FOUND(404, "체크리스트 항목을 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(409, "이미 사용 중인 이메일입니다."),
    LOGIN_FAILED(400, "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_FRIEND_REQUEST(400, "잘못된 친구 요청입니다."),
    FRIEND_REQUEST_ALREADY_SENT(400, "이미 같은 대상에게 친구 요청을 보냈습니다."),
    FRIEND_REQUEST_ALREADY_PROCESSED(400, "이미 처리된 친구 요청입니다."),
    FRIEND_REQUEST_ALREADY_RECEIVED(400, "상대방이 이미 친구 요청을 보냈습니다."),
    FRIEND_REQUEST_NOT_FOUND(404, "친구 요청 정보를 찾을 수 없습니다."),
    FRIEND_RELATION_NOT_FOUND(404, "친구 관계를 찾을 수 없습니다."),
    FRIEND_ALREADY_EXISTS(400, "이미 친구 관계입니다."),
    NO_ACCESS_TOKEN(401, "액세스 토큰이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(402, "리프레시 토큰이 유효하지 않습니다."),
    ACCESS_DENIED(403, "접근 권한이 존재하지 않습니다."),
    UNAUTHORIZED(401, "로그인이 필요합니다."),
    RESOURCE_RELATION_MISMATCH(400, "리소스 간의 관계가 일치하지 않습니다."),
    REQUEST_LIST_EMPTY(400, "요청 리스트가 비어있습니다."),
    SHARING_NOT_ALLOWED(400, "공유는 비공개 상태일 때 가능합니다."),
    INVALID_QUERY_PARAMETER(400, "유효하지 않은 쿼리 파라미터입니다.");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
