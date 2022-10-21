package com.assignment.comit.global.configuration.security.jwt

import com.assignment.comit.domain.member.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class TokenProvider {
    @Value("\${jwt.secret}")
    private val SECRET_KEY: String = ""
    private val ACCESS_TOKEN_EXPIRE_TIME: Long = 1000L * 60 * 60 * 3
    private val REFRESH_TOKEN_EXPIRE_TIME: Long = ACCESS_TOKEN_EXPIRE_TIME/3 * 24 * 30

    private enum class TokenType(val value: String){
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken"),
    }
    private enum class TokenClaimName(val value: String){
        EMAIL("userEmail"),
        TOKEN_TYPE("tokenType"),
        ROLE("roles"),
    }

    private fun getSignInKey(): Key {
        val bytes = SECRET_KEY.toByteArray()
        return Keys.hmacShaKeyFor(bytes)
    }

    private fun extractAllClaims(token: String): Claims {
        val replaceToken = token.replace("Bearer ", "")
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(replaceToken)
            .body
    }

    fun getUserEmail(token: String) = extractAllClaims(token).get(TokenClaimName.EMAIL.value, String::class.java)

    fun getTokenType(token: String) = extractAllClaims(token).get(TokenClaimName.TOKEN_TYPE.value, String::class.java)

    fun isTokenExpired(token: String): Boolean{
        try{
            extractAllClaims(token).expiration
            return false
        } catch (ex: ExpiredJwtException){
            return true
        }
    }

    fun generateAccessToken(email: String, roles: List<Role>, tokenType: String): String{
        val claims = Jwts.claims()
        claims.put(TokenClaimName.ROLE.value, roles)
        claims.put(TokenClaimName.TOKEN_TYPE.value, TokenType.ACCESS_TOKEN.value)
        return createToken(claims, email)
    }

    fun generateRefreshToken(email: String): String{
        val claims = Jwts.claims()
        claims.put(TokenClaimName.TOKEN_TYPE.value, TokenType.REFRESH_TOKEN.value)
        return createToken(claims, email)
    }

    private fun createToken(claims: Claims, email: String): String {
        claims.put(TokenClaimName.EMAIL.value, email)
        return Jwts.builder()
            .addClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis()))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }
}