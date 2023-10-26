package com.example.demo.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val jwtFilter: JwtFilter
) {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(userDetailsService)
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        return daoAuthenticationProvider
    }

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/login", "/auth/register").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}