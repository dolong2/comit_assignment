package com.assignment.comit.global.exception.collection

import com.assignment.comit.global.exception.ErrorCode

class PasswordNotCorrectException: BaseException(
    ErrorCode.PASSWORD_NOT_CORRECT
)