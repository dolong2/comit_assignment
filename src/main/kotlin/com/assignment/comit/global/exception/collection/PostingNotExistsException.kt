package com.assignment.comit.global.exception.collection

import com.assignment.comit.global.exception.ErrorCode

class PostingNotExistsException: BaseException(
    ErrorCode.POSTING_NOT_EXIST
)