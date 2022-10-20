package com.assignment.comit.domain.posting

import com.assignment.comit.domain.member.Member
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Posting(
    val title: String,
    val content: String,
    @ManyToOne
    @JoinColumn(name = "owner")
    val owner: Member,
){
    @Id @GeneratedValue
    val id: Long = 0
}