package com.example.demo.book

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "BOOKS")
data class Book (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(columnDefinition = "TEXT")
        var name: String,

        @Column(columnDefinition = "TEXT")
        var image: String,

        @Column(columnDefinition = "TEXT", nullable = true)
        var description: String?,

        @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
        var isRead: Boolean,

        @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
        var rating: Double,

        @Column(nullable = true)
        var purchaseDate: LocalDate?,

        @Column(nullable = true)
        var completionDate: LocalDate?
) {
}