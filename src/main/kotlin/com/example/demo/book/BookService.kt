package com.example.demo.book

import com.example.demo.book.dtos.CreateBookDto
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

    /**
     * Create a new book
     * @param newBook CreateBookDto
     * @return Created book
     * @throws HttpStatus.BAD_REQUEST User id is nor defined!
     */
    fun create(newBook: CreateBookDto): Book {
        if(newBook.userId == null){
            throw  throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is nor defined!")
        }
        return this.db.save(Book(newBook))
    }

    fun delete(id: Long) {
        return this.db.deleteById(id)
    }

    fun update(id: Long, book: Book): Book {
        if(!this.db.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "The entity with id $id does not exist")
        }
        return this.db.save(book)
    }

    /**
     * Get entity by id
     * @param id Long
     * @throws HttpStatus.NOT_FOUND The entity with id does not exist
     * @return Book
     */
    fun getOne(id: Long): Book {
        if(!this.db.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "The entity with id $id does not exist")
        }
        return this.db.getOne(id)
    }

    /**
     * Get books by user id
     * @param pageable Pageable
     * @param userId Long
     * @return Page of books related to user id
     */
    fun getByUserId(pageable: Pageable, userId: Long): Page<Book> {
        return db.getByUserId(userId, pageable)
    }
}