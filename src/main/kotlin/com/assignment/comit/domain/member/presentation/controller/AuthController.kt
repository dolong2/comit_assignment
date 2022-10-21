package com.assignment.comit.domain.member.presentation.controller

import com.assignment.comit.domain.member.presentation.dto.request.SigninReqDto
import com.assignment.comit.domain.member.presentation.dto.request.SignupReqDto
import com.assignment.comit.domain.member.presentation.dto.response.RefreshResDto
import com.assignment.comit.domain.member.presentation.dto.response.SigninResDto
import com.assignment.comit.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val memberService: MemberService,
){

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupReqDto: SignupReqDto): ResponseEntity<Void> {
        println("signupReqDto.email = ${signupReqDto.email}")
        memberService.join(signupReqDto)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/signin")
    fun signin(@Valid @RequestBody signinReqDto: SigninReqDto): ResponseEntity<SigninResDto>{
        return ResponseEntity.ok(memberService.login(signinReqDto))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestHeader refreshToken: String): ResponseEntity<RefreshResDto>{
        return ResponseEntity.ok(memberService.refresh(refreshToken))
    }
}