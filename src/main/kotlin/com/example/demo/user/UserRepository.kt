package com.example.demo.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    /**
     * Get the count for how many times the email is registered
     * @param email String
     * @return number representing how many times the email is registered
     */
    @Query("SELECT COUNT(*) AS Same FROM users WHERE email LIKE CONCAT('%', :email, '%')", nativeQuery = true)
    fun searchEmail(email: String?): Long

    /**
     * Get user by email
     * @param email String
     * @return Optional of user
     */
    fun getByEmail(email: String): Optional<User>

    /**
     * @param email String
     * @return User based on email
     */
    @Query("SELECT * FROM users WHERE email LIKE :email", nativeQuery = true)
    fun getNotOptionalByEmail(email: String?): User
}