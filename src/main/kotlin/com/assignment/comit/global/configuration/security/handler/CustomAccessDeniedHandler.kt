package com.assignment.comit.global.configuration.security.handler

import com.assignment.comit.global.util.ErrorResponseWriter
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
):AccessDeniedHandler {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)
    private val errorResponseWriter = ErrorResponseWriter(log, objectMapper)
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        errorResponseWriter.writeErrorResponse(request, response, accessDeniedException)
    }
}