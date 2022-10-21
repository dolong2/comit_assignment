package com.assignment.comit.global.exception

import com.assignment.comit.global.exception.collection.BaseException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(ex: BaseException, request: HttpServletRequest): ResponseEntity<ErrorResponse>{
        log.error(request.requestURI)
        log.error(ex.message)
        ex.printStackTrace()
        val errorResponse = ErrorResponse(ex.errorCode.code, ex.errorCode.msg)
        return ResponseEntity(errorResponse, HttpStatus.valueOf(ex.errorCode.code))
    }

}