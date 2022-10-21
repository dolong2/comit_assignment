package com.assignment.comit.domain.member.presentation.controller

import com.assignment.comit.domain.member.presentation.dto.request.SigninReqDto
import com.assignment.comit.domain.member.presentation.dto.request.SignupReqDto
import com.assignment.comit.domain.member.presentation.dto.response.RefreshResDto
import com.assignment.comit.domain.member.presentation.dto.response.SigninResDto
import com.assignment.comit.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val memberService: MemberService,
){

    @PostMapping("/signup")
    fun signup(signupReqDto: SignupReqDto): ResponseEntity<Void> {
        memberService.join(signupReqDto)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/signin")
    fun signin(signinReqDto: SigninReqDto): ResponseEntity<SigninResDto>{
        return ResponseEntity.ok(memberService.login(signinReqDto))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestHeader refreshToken: String): ResponseEntity<RefreshResDto>{
        return ResponseEntity.ok(memberService.refresh(refreshToken))
    }
}