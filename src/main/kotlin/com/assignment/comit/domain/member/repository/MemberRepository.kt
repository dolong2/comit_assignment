package com.assignment.comit.domain.member.repository

import com.assignment.comit.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String?): Member?
}