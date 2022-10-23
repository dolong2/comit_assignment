package com.assignment.comit.domain.member.presentation.dto.response

import com.assignment.comit.domain.member.Member

class MemberResDto(
    val id: Long,
    val name: String
){
    constructor(member: Member) : this(
        id = member.id,
        name = member.name
    )
}