package com.example.demo.book

import com.example.demo.book.dtos.CreateBookDto
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "BOOKS")
class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(columnDefinition = "integer", nullable = false)
        var userId: Long? = null,

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
        var purchaseDate: String? = null,

        @Column(nullable = true)
        var completionDate: String? = null
) : Serializable {

        constructor(createBookDto: CreateBookDto): this() {
                this.name = createBookDto.name
                this.image = createBookDto.image
                this.description = createBookDto.description
                this.isRead = createBookDto.isRead
                this.rating = createBookDto.rating
                this.purchaseDate = createBookDto.purchaseDate.toString()
                this.completionDate = createBookDto.completionDate.toString()
                this.userId = createBookDto.userId?.toLong()
        }
}