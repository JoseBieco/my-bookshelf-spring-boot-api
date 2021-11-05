package com.example.demo.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    @Autowired
    val db: UserRepository
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
            password = user.password
        ))
    }
}