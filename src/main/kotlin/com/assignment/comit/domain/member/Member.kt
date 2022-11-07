package com.assignment.comit.domain.member

import com.assignment.comit.domain.posting.Posting
import org.hibernate.validator.constraints.UniqueElements
import javax.persistence.*

@Entity
class Member(
    @UniqueElements
    val email: String,
    val name: String,
    val password:String,
    @Enumerated(EnumType.STRING) @Column(name = "Role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "member_id")])
    var roles: MutableList<Role>,
){
    @Id @GeneratedValue
    val id: Long = 0
    var refreshToken: String = ""
        private set
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "owner")
    val postings: List<Posting> = mutableListOf()

    fun updateRefreshToken(refreshToken: String){
        this.refreshToken = refreshToken
    }
}