package model.service

import model.User
import model.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(username: String, password: String): User {
        val encoder = BCryptPasswordEncoder()
        val hashedPassword = encoder.encode(password)
        val user = User(userName = username, password = hashedPassword)
        return userRepository.save(user)
    }

    fun getUserByUsername(username: String): User? {
        return userRepository.findByUserName(username)
    }
}
