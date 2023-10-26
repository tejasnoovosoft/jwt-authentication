package com.example.demo.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name="username",unique = true)
    val userUsername: String,
    @Column(name="password",unique = true)
    val userPassword: String
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return userPassword
    }

    override fun getUsername(): String {
        return userUsername
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