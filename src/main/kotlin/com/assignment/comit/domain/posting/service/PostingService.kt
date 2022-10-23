package com.assignment.comit.domain.posting.service

import com.assignment.comit.domain.member.Role
import com.assignment.comit.domain.posting.presentation.dto.req.PostingReqDto
import com.assignment.comit.domain.posting.presentation.dto.res.PostingListResDto
import com.assignment.comit.domain.posting.presentation.dto.res.PostingResDto
import com.assignment.comit.domain.posting.repository.PostingRepository
import com.assignment.comit.global.exception.collection.MemberNotSameException
import com.assignment.comit.global.exception.collection.PostingNotExistsException
import com.assignment.comit.global.util.CurrentMemberUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostingService(
    private val postingRepository: PostingRepository,
    private val currentMemberUtil: CurrentMemberUtil,
){
    @Transactional(rollbackFor = [Exception::class])
    fun writePosting(postingReqDto: PostingReqDto){
        val member = currentMemberUtil.getCurrentMember()
        postingRepository.save(postingReqDto.toEntity(member))
    }

    @Transactional(rollbackFor = [Exception::class])
    fun deletePosting(id: Long){
        val posting = postingRepository.findById(id)
            .orElseThrow { PostingNotExistsException() }
        val member = currentMemberUtil.getCurrentMember()
        if(posting.owner == member || member.roles.contains(Role.ROLE_ADMIN))
            throw MemberNotSameException()
        postingRepository.delete(posting)
    }

    @Transactional(rollbackFor = [Exception::class], readOnly = true)
    fun getAllPosting(): PostingListResDto{
        return PostingListResDto(
            postingRepository.findAll()
                .map { PostingResDto(it) }
        )
    }

    @Transactional(rollbackFor = [Exception::class], readOnly = true)
    fun getMyAllPosting(): PostingListResDto{
        return PostingListResDto(
            postingRepository.findAll()
                .filter { it.owner == currentMemberUtil.getCurrentMember() }
                .map { PostingResDto(it) }
        )
    }

    @Transactional(rollbackFor = [Exception::class], readOnly = true)
    fun getOnePosting(id: Long): PostingResDto{
        val posting = postingRepository.findById(id)
            .orElseThrow { PostingNotExistsException() }
        return PostingResDto(posting)
    }
}