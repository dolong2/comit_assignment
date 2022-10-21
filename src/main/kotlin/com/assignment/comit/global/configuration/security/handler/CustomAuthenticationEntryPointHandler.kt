package com.assignment.comit.global.configuration.security.handler

import com.assignment.comit.global.util.ErrorResponseWriter
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPointHandler(
    private val objectMapper: ObjectMapper,
): AuthenticationEntryPoint {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)
    private val errorResponseWriter = ErrorResponseWriter(log, objectMapper)

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        errorResponseWriter.writeErrorResponse(request, response, authException)
    }
}