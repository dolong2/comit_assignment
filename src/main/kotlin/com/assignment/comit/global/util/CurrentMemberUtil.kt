package com.assignment.comit.global.util

import com.assignment.comit.domain.member.Member
import com.assignment.comit.domain.member.repository.MemberRepository
import com.assignment.comit.global.configuration.security.auth.AuthDetails
import com.assignment.comit.global.exception.collection.MemberNotExistException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class CurrentMemberUtil(
    private val memberRepository: MemberRepository
){
    private fun getCurrentEmail():String =
        (SecurityContextHolder.getContext().authentication.principal as AuthDetails)
            .getEmail()

    fun getCurrentMember(): Member =
        memberRepository.findByEmail(getCurrentEmail())?:throw MemberNotExistException()
}