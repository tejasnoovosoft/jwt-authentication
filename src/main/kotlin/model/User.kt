package model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val userId: Long? = null,
    @Column(name = "username",unique = true)
    val userName: String,
    @Column(name = "password")
    val password: String,
)