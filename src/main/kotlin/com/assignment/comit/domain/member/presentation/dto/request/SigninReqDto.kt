package com.assignment.comit.domain.member.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SigninReqDto(
    @Email
    @NotBlank
    val email: String,
    @NotBlank
    @Size(min = 8, max = 25)
    val password: String,
)