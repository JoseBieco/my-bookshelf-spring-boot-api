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

    fun login(login: LoginDto): Boolean {
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

        return this.passwordEncoder.matches(login.password, this.db.getOne(12).password)
    }
}