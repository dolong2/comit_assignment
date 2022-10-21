package com.assignment.comit.domain.member.presentation.dto.request

import com.assignment.comit.domain.member.Member
import com.assignment.comit.domain.member.Role
import java.util.Collections
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignupReqDto(
    @field:Email
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    @field:Size(min = 8, max = 25)
    val password: String,
) {
    fun toEntity(password: String): Member{
        return Member(
            email = email,
            name = name,
            password = password,
            roles = Collections.singletonList(Role.ROLE_MEMBER)
        )
    }
}