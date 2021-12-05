package com.example.demo.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    // Get the count for how many times the email is registered
    /**
     * Get the count for how many times the email is registered
     * @param email String
     * @return number representing how many times the email is registered
     */
    @Query("SELECT COUNT(*) AS Same FROM users WHERE email LIKE CONCAT('%', :email, '%')", nativeQuery = true)
    fun searchEmail(email: String?): Long

    fun getByEmail(email: String): Optional<User>
}