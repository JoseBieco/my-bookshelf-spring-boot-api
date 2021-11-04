package com.example.demo.book

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookService(
    @Autowired
    val db: BookRepository
) {
    fun getAll(): MutableIterable<Book> = db.findAll()

    fun create(newBook: Book): Book {
        return this.db.save(Book(
            name = newBook.name,
            image = newBook.image,
            description = newBook.description,
            isRead = newBook.isRead,
            rating = newBook.rating,
            purchaseDate = newBook.purchaseDate,
            completionDate = newBook.completionDate
        ))
    }
}