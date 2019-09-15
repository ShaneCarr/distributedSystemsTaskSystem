package com.shcarrdistributedtasks.distributedtasks.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User : UserDetails {

    @Id
    var id: Int? = null

    var login: String? = null

    private var password: String? = null

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return AuthorityUtils.createAuthorityList("USER")
    }

    override fun getPassword(): String? {
        return this.password
    }

    override fun getUsername(): String? {
        return this.login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
