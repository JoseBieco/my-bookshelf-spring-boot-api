package com.example.demo.user

import com.example.demo.user.dtos.RegisterUserDto
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.crypto.password.PasswordEncoder
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "USERS")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT",  nullable = false)
    var name: String? = null,

    @Column(columnDefinition = "TEXT",  nullable = false, unique = true)
    var email: String? = null,

    @Column(columnDefinition = "TEXT",  nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String? = null,

    @Column(columnDefinition = "Boolean", nullable = false)
    var isAdmin: Boolean = false,

    var token: String = ""
): Serializable {

    constructor(registerUserDto: RegisterUserDto, passwordEncoder: PasswordEncoder): this() {
        this.name = registerUserDto.name
        this.email = registerUserDto.email
        this.password = passwordEncoder.encode(registerUserDto.password)
        this.isAdmin = registerUserDto.isAdmin
    }
}