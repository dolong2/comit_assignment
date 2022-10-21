package com.assignment.comit.global.exception.collection

import com.assignment.comit.global.exception.ErrorCode

class RefreshTokenExpiredException : BaseException(
    ErrorCode.TOKEN_EXPIRED
)