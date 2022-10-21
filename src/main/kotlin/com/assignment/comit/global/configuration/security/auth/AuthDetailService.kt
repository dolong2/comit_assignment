package com.assignment.comit.global.configuration.security.auth

import com.assignment.comit.domain.member.repository.MemberRepository
import com.assignment.comit.global.exception.collection.MemberNotExistException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailService(
    private val memberRepository: MemberRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        AuthDetails(memberRepository.findByEmail(username)
            ?: throw MemberNotExistException()
        )
}