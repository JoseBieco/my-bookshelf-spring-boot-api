package com.example.demo.auth

import com.example.demo.auth.dto.LoggedDto
import com.example.demo.auth.dto.LoginDto
import com.example.demo.auth.dto.RegisterDto
import com.example.demo.auth.dto.TokenDto
import com.example.demo.config.UserDetailsServiceImpl
import com.example.demo.jwt.JwtService
import com.example.demo.user.User
import com.example.demo.user.UserRepository
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class AuthService(
    @Autowired
    val db: UserRepository,

    @Autowired
    val jwtService: JwtService,

    @Autowired
    val passwordEncoder: PasswordEncoder,

    @Autowired
    val userDetails: UserDetailsServiceImpl,

    @Value("\${security.jwt.signatureKey}")
    private val signatureKey: String,
) {


    /**
     * Create new entity
     * @param user RegisterDto
     * @return The created User
     * @throws HttpStatus.BAD_REQUEST This email is already registered
     */
    fun create(user: RegisterDto): User {
        /**
         * TODO
         * Validate email -> must be unique
         * throw error if it's not unique
         */
        return db.getByEmail(user.email)
            .takeIf { it !== null }
            ?.run { throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "This email is already registered") }
            ?: run { User(user).apply { encodePassword(passwordEncoder) }.run { db.save(this) } }
    }

    /**
     * Remove user from database
     * @param userId User unique identifier
     */
    fun delete(userId: Long) {
        // Validate if the userId exists in database
        return db.findById(userId)
            .orElseThrow{
                ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
            }.run { db.delete(this) }
    }

    /**
     * Update user password
     */
    fun updatePassword(userId: Long, newPassword: String) {
        return db.findById(userId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
        }.apply {
            password = newPassword
            encodePassword(passwordEncoder)
            updatedAt = LocalDateTime.now()
        }.run { db.save(this) }
    }

    /**
     * Get user from sent token
     * @throws HttpStatus.UNAUTHORIZED Unauthorized
     * @return User
     */
    fun getUserFromToken(token: String): User {
        val id = Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).body.issuer.toLong()
        return db.getById(id)
    }

    fun authenticate(loginDto: LoginDto): Boolean {
        val user: UserDetails = loginDto.email.let { userDetails.loadUserByUsername(it) }
        return passwordEncoder.matches(loginDto.password, user.password)
    }

    /**
     * Login method
     * @param login LoginDto
     * @return Valid User
     * @throws HttpStatus.UNAUTHORIZED Unauthorized
     */
    fun login(login: LoginDto): LoggedDto {
        return LoggedDto(authenticate(login).takeIf { it }
            ?.run { db.getByEmail(login.email) }?.apply { this.token = jwtService.generateToken(this) }
            .run { this?.let { db.save(it)} }
            ?: run { throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized!")})
    }

    /**
     * From token, get the user and set it's token to null
     * @param token User's token
     */
    fun logout(token: TokenDto) {
        this.getUserFromToken(token.token)
            .apply { this.token = null }.run { db.save(this)}
    }
}