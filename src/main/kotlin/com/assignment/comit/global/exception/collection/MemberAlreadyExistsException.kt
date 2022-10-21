package com.assignment.comit.global.exception.collection

import com.assignment.comit.global.exception.ErrorCode

class MemberAlreadyExistsException: BaseException(
    ErrorCode.USER_ALREADY_EXIST
)