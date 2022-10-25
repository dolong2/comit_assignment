package com.assignment.comit.domain.posting.presentation.controller

import com.assignment.comit.domain.posting.presentation.dto.req.PostingReqDto
import com.assignment.comit.domain.posting.presentation.dto.res.PostingListResDto
import com.assignment.comit.domain.posting.presentation.dto.res.PostingResDto
import com.assignment.comit.domain.posting.service.PostingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/posting")
class PostingController(
    val postingService: PostingService,
){
    @PostMapping
    fun writePosting(@Valid @RequestBody postingReqDto: PostingReqDto): ResponseEntity<Void>{
        postingService.writePosting(postingReqDto)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deletePosting(@PathVariable id: Long): ResponseEntity<Void>{
        postingService.deletePosting(id)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getAllPosting(): ResponseEntity<PostingListResDto> =
        ResponseEntity.ok(postingService.getAllPosting())

    @GetMapping("/my")
    fun getMyAllPosting(): ResponseEntity<PostingListResDto> =
        ResponseEntity.ok(postingService.getMyAllPosting())

    @GetMapping("/{id}")
    fun getOnePosting(@PathVariable id: Long): ResponseEntity<PostingResDto> =
        ResponseEntity.ok(postingService.getOnePosting(id))
}