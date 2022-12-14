package com.assignment.comit.global.exception

enum class ErrorCode (
    val code: Int,
    val msg: String,
){
    BAD_REQUEST(400, "올바르지 않은 요청입니다."),
    FORBIDDEN(403, "금지된 요청입니다."),
    UNAUTHORIZED(401, "권한이 없습니다."),
    NOT_FOUND(404, "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 에러"),

    NOT_EXIST_MEMBER(404, "해당 유저가 존재하지 않습니다."),
    USER_ALREADY_EXIST(400, "유저가 이미 존재합니다."),
    PASSWORD_NOT_CORRECT(400, "패스워드가 일치하지 않습니다."),
    POSTING_NOT_EXIST(404, "해당 포스팅이 존재하지 않습니다."),
    TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),
    TOKEN_NOT_VALID(401, "토큰이 유효하지 않습니다."),
    USER_NOT_SAME(401, "유저가 일치하지 않습니다.")
}