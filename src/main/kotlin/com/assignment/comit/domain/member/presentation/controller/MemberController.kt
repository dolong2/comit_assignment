package com.assignment.comit.domain.member.presentation.controller

import com.assignment.comit.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/member")
class MemberController(
    val memberService: MemberService,
){
    @PostMapping("/logout")
    fun logout(): ResponseEntity<Void>{
        memberService.logout()
        return ResponseEntity.ok().build()
    }
}