package com.assignment.comit.domain.posting.facade

import com.assignment.comit.domain.posting.repository.PostingRepository
import com.assignment.comit.global.exception.collection.PostingNotExistsException
import org.springframework.stereotype.Component

@Component
class PostingFacade(
    val postingRepository: PostingRepository,
){
    fun getOnePosting(id: Long) =
        postingRepository.findById(id)
            .orElseThrow { PostingNotExistsException() }
}