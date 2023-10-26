package com.example.demo.controller

import com.example.demo.dtos.LoginDTO
import com.example.demo.dtos.RegisterDTO
import com.example.demo.model.User
import com.example.demo.service.CustomUserDetailService
import com.example.demo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val customUserDetailService: CustomUserDetailService,
) {

    @PostMapping("/register")
    fun register(@RequestBody registerDTO: RegisterDTO): User {
        return userService.addUser(registerDTO)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<Any> {
        return userService.loginUser(loginDTO)
    }

    @GetMapping("/current")
    fun getLoggedInUser(principal: Principal): UserDetails {
        val username = principal.name
        return customUserDetailService.loadUserByUsername(username)
    }
}