package com.example.demo.user

import com.example.demo.user.dtos.LoginDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

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
     * @throws HttpStatus.BAD_REQUEST This email is already registered
     */
    fun create(user: User): User {
        /**
         * Validate email -> must be unique
         * throw error if it's not unique
         */
        if(this.db.searchEmail(user.email) > 0){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already registered")
        }

        // TODO: encrypt password
        return this.db.save(User(
            name = user.name,
            email = user.email,
            password = this.passwordEncoder.encode(user.password)
        ))
    }

    /**
     * Login method
     * @param login LoginDto
     * @return Valid User
     * @throws HttpStatus.BAD_REQUEST Invalid email or password
     * @throws HttpStatus.UNAUTHORIZED Unauthorized
     */
    fun login(login: LoginDto): User {
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

        val user = this.db.getByEmail(login.email)
        if(!this.passwordEncoder.matches(login.password, user.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized!")
        }

        return user
    }
}