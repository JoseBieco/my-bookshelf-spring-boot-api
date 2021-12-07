package com.example.demo.book

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, Long>{

    @Query("SELECT a FROM Book a ")
    fun find(pageable: Pageable): Page<Book>

    /**
     * Get all books related to user id
     * @param userId Long
     * @param pageable Pageable
     * @return Page of books related to passed user id
     */
    fun getByUserId(userId: Long, pageable: Pageable): Page<Book>
}