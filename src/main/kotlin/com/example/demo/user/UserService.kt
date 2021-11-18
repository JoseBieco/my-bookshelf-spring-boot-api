package com.example.demo.user

import com.example.demo.user.dtos.LoginDto
import com.example.demo.user.dtos.RegisterUserDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Service
class UserService(
    @Autowired
    val db: UserRepository,

    @Autowired
    val passwordEncoder: PasswordEncoder
) {

    /**
     * Register new user
     * @param user User
     * @return Created User
     * @throws HttpStatus.BAD_REQUEST Invalid user information
     * @throws HttpStatus.BAD_REQUEST This email is already registered
     */
    fun create(user: RegisterUserDto): User {

        if(!user.validate()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user information!")
        }

        /**
         * Validate email -> must be unique
         * throw error if it's not unique
         */
        if(this.db.searchEmail(user.email) > 0){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already registered")
        }

        return this.db.save(User(user, this.passwordEncoder))
    }

    /**
     * Login method
     * @param login LoginDto
     * @return Valid User
     * @throws HttpStatus.BAD_REQUEST Invalid email or password
     * @throws HttpStatus.UNAUTHORIZED Unauthorized
     */
    fun login(login: LoginDto, response: HttpServletResponse): User {
        /**
         * Validate email and password;
         * If not valid, throw 400;
         *
         * Check if email is registered and if the password match,
         * If the password not match, throw 401
         */
        if(!login.validate()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password!")
        }

        // Check if the db have the user email and then get the user to validate the password
        if(this.db.searchEmail(login.email) == 0L) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not registered yet!")
        }
        val user = this.db.getByEmail(login.email)

        if(!this.passwordEncoder.matches(login.password, user.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized!")
        }

        val issuer = user.id.toString()

        // TODO: create a '.env' to the secret
        val token = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        val cookie = Cookie("token", token)
        cookie.isHttpOnly = true

        response.addCookie(cookie)
        return user
    }

    /**
     * Delete entity by id
     * @param id Long
     */
    fun delete(id: Long) {
        // TODO: Validate if user with id exists
        return this.db.deleteById(id)
    }
}