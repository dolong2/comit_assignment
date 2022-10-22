package com.assignment.comit.domain.posting.repository

import com.assignment.comit.domain.posting.Posting
import org.springframework.data.jpa.repository.JpaRepository

interface PostingRepository: JpaRepository<Posting, Long> {
}