package com.assignment.comit.domain.member.service

import com.assignment.comit.domain.member.presentation.dto.request.SigninReqDto
import com.assignment.comit.domain.member.presentation.dto.request.SignupReqDto
import com.assignment.comit.domain.member.presentation.dto.response.RefreshResDto
import com.assignment.comit.domain.member.presentation.dto.response.SigninResDto
import com.assignment.comit.domain.member.repository.MemberRepository
import com.assignment.comit.global.configuration.security.jwt.TokenProvider
import com.assignment.comit.global.exception.collection.*
import com.assignment.comit.global.util.CurrentMemberUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val currentMemberUtil: CurrentMemberUtil,
){

    fun join(signupReqDto: SignupReqDto): Long{
        if(memberRepository.existsByEmail(signupReqDto.email))
            throw MemberAlreadyExistsException()
        val password = passwordEncoder.encode(signupReqDto.password)
        val member = signupReqDto.toEntity(password)
        return memberRepository.save(member).id
    }

    fun login(signinReqDto: SigninReqDto): SigninResDto {
        if(!memberRepository.existsByEmail(signinReqDto.email))
            throw MemberNotExistException()
        val member = memberRepository.findByEmail(signinReqDto.email)!!
        if(passwordEncoder.matches(signinReqDto.password, member.password))
            throw PasswordNotCorrectException()
        val accessToken = tokenProvider.generateAccessToken(member.email, member.roles)
        val refreshToken = tokenProvider.generateRefreshToken(member.email)
        member.updateRefreshToken(refreshToken)
        return SigninResDto(
            email = member.email,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    fun logout(){
        val member = currentMemberUtil.getCurrentMember()
        member.updateRefreshToken("")
    }

    fun refresh(refreshToken: String): RefreshResDto {
        if(tokenProvider.isTokenExpired(refreshToken))
            throw RefreshTokenExpiredException()
        if(tokenProvider.getTokenType(refreshToken)=="refreshToken")
            throw TokenNotValidException()
        val member = memberRepository.findByEmail(tokenProvider.getUserEmail(refreshToken))!!
        val accessToken = tokenProvider.generateAccessToken(member.email, member.roles)
        val refreshToken = tokenProvider.generateRefreshToken(member.email)
        return RefreshResDto(
            email = member.email,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}