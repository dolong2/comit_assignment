package com.assignment.comit.global.configuration.security.jwt

import com.assignment.comit.global.exception.collection.AccessTokenExpiredException
import com.assignment.comit.global.exception.collection.TokenNotValidException
import com.assignment.comit.global.util.ErrorResponseWriter
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtExceptionFilter(
    private val objectMapper: ObjectMapper,
): OncePerRequestFilter(){
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)
    private val errorResponseWriter = ErrorResponseWriter(log, objectMapper)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: AccessTokenExpiredException) {
            errorResponseWriter.writeErrorResponse(request, response, e)
        } catch(e: TokenNotValidException){
            errorResponseWriter.writeErrorResponse(request, response, e)
        } catch (e: Exception) {
            errorResponseWriter.writeErrorResponse(request, response, e)
        }
    }
}