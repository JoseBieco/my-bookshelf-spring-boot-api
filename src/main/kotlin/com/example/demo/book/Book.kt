package com.example.demo.book

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "BOOKS")
class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(columnDefinition = "TEXT",  nullable = false)
        var name: String? = null,

        @Column(columnDefinition = "TEXT", nullable = true)
        var image: String? = null,

        @Column(columnDefinition = "TEXT", nullable = true)
        var description: String? = null,

        @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
        var isRead: Boolean? = null,

        @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
        var rating: Double? = null,

        @Column(nullable = true)
        var purchaseDate: LocalDate? = null,

        @Column(nullable = true)
        var completionDate: LocalDate? = null
) : Serializable {
}