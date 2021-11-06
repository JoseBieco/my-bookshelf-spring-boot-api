package com.example.demo.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long> {

    // Get the count for how many times the email is registered
    @Query("SELECT COUNT(*) AS Same FROM users WHERE email LIKE CONCAT('%', :email, '%')", nativeQuery = true)
    fun searchEmail(email: String?): Long

    /**
     * @param email String
     * @return User based on email
     */
    @Query("SELECT * FROM users WHERE email LIKE :email", nativeQuery = true)
    fun getByEmail(email: String?): User
}