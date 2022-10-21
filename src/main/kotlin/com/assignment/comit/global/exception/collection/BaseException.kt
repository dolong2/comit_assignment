package com.assignment.comit.global.exception.collection

import com.assignment.comit.global.exception.ErrorCode

open class BaseException(
    val errorCode: ErrorCode,
): RuntimeException(errorCode.msg)