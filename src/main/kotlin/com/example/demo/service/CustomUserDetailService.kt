package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUserUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
        return User(user.id, user.username, user.password)
    }
}