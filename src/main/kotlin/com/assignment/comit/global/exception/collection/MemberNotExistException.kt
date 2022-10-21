package com.assignment.comit.global.exception.collection

import com.assignment.comit.global.exception.ErrorCode

class MemberNotExistException: BaseException(
    ErrorCode.NOT_EXIST_MEMBER
)