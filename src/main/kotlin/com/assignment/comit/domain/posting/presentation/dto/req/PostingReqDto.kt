package com.assignment.comit.domain.posting.presentation.dto.req

import com.assignment.comit.domain.member.Member
import com.assignment.comit.domain.posting.Posting

class PostingReqDto(
    val title: String,
    val content: String,
){
    fun toEntity(member: Member): Posting{
        return Posting(
            title = title,
            content =  content,
            owner = member
        )
    }
}