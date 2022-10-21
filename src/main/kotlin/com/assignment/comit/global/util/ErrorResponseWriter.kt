package com.assignment.comit.global.util

import com.assignment.comit.global.exception.collection.BaseException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ErrorResponseWriter(
    private val log: Logger,
    private val objectMapper: ObjectMapper,
){
    @Throws(IOException::class)
    fun writeErrorResponse(
        request: HttpServletRequest,
        response: HttpServletResponse,
        e: BaseException,
    ) {
        log.error(request.requestURI)
        log.error(e.errorCode.msg)
        e.printStackTrace()
        writeBody(response, e)
    }

    @Throws(IOException::class)
    fun writeErrorResponse(request: HttpServletRequest, response: HttpServletResponse, e: Exception) {
        log.error(request.requestURI)
        log.error("알수없는 에러")
        e.printStackTrace()
        writeBody(response, e)
    }

    @Throws(IOException::class)
    private fun writeBody(response: HttpServletResponse, e: BaseException) {
        val json = convertJson(e)
        response.status = e.errorCode.code
        response.contentType = "Application/json"
        response.writer.write(json)
    }

    @Throws(IOException::class)
    private fun writeBody(response: HttpServletResponse, e: Exception) {
        val json = convertJson(e)
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.contentType = "Application/json"
        response.writer.write(json)
    }

    @Throws(JsonProcessingException::class)
    private fun convertJson(e: Exception): String {
        val map: MutableMap<String, Any?> = HashMap()
        map["msg"] = e.message
        map["code"] = HttpStatus.INTERNAL_SERVER_ERROR.value()
        return objectMapper.writeValueAsString(map)
    }
}