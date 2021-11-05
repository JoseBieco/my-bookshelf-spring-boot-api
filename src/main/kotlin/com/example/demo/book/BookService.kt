package com.example.demo.book

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class BookService(
    @Autowired
    val db: BookRepository
) {
    fun getAll(pageable: Pageable): Page<Book> {
        return db.find(pageable)
    }

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

    fun delete(id: Long) {
        return this.db.deleteById(id)
    }

    fun update(id: Long, book: Book): Book {
        if(!this.db.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "The entity with id $id does not exist")
        }
        //var updateBook = this.db.findById(id)

        return this.db.save(book)
    }

    fun getOne(id: Long): Book {
        if(!this.db.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "The entity with id $id does not exist")
        }
        return this.db.getOne(id)
    }
}