package com.example.demo.service

import com.example.demo.dtos.LoginDTO
import com.example.demo.dtos.RegisterDTO
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import com.example.demo.security.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
    private val customUserDetailService: CustomUserDetailService
) {

    fun addUser(registerDTO: RegisterDTO): User {
        val user = User(
            userUsername = registerDTO.username, userPassword = registerDTO.password
        )
        val encodedPassword = passwordEncoder.encode(user.password)
        val userToSave = user.copy(userPassword = encodedPassword)
        return userRepository.save(userToSave)
    }

    fun loginUser(loginDTO: LoginDTO): ResponseEntity<Any> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDTO.username,
                loginDTO.password
            )
        )

        val user = customUserDetailService.loadUserByUsername(loginDTO.username)
        val jwtToken = jwtUtil.generateToken(user)
        return ResponseEntity.ok(jwtToken)
    }

/*    fun comparePassword(loginPassword: String, userPassword: String): Boolean {
        return passwordEncoder.matches(loginPassword, userPassword)
    }*/
}