package com.assignment.comit.global.configuration.security.jwt

import com.assignment.comit.global.configuration.security.auth.AuthDetailService
import com.assignment.comit.global.exception.collection.AccessTokenExpiredException
import com.assignment.comit.global.exception.collection.TokenNotValidException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtReqFilter(
    private val tokenProvider: TokenProvider,
    private val authDetailService: AuthDetailService,
): OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val accessToken = request.getHeader("Authorization")
        if(accessToken!=null){
            if(tokenProvider.isTokenExpired(accessToken)){
                throw AccessTokenExpiredException()//토큰만료
            }else if(!tokenProvider.getTokenType(accessToken).equals("accessToken")){
                throw TokenNotValidException()
            }
            registerSecurityContext(request, accessToken)
        }
        filterChain.doFilter(request, response)
    }

    fun registerSecurityContext(request: HttpServletRequest, accessToken: String){
        val userDetail = authDetailService.loadUserByUsername(tokenProvider.getUserEmail(accessToken))
        val authenticationToken = UsernamePasswordAuthenticationToken(userDetail, null, userDetail.authorities)
        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }
}