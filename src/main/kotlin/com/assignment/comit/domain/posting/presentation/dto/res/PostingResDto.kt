package com.assignment.comit.domain.posting.presentation.dto.res

import com.assignment.comit.domain.member.presentation.dto.response.MemberResDto
import com.assignment.comit.domain.posting.Posting

class PostingResDto(
    val id: Long,
    val title: String,
    val content: String,
    val owner: MemberResDto,
){
    constructor(posting: Posting): this(
        id = posting.id,
        title = posting.title,
        content = posting.content,
        owner = MemberResDto(posting.owner)
    )
}