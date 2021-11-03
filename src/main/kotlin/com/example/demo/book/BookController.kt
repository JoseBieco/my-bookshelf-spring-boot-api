package com.example.demo.book

import com.example.demo.book.dtos.BookDTO
import com.example.demo.book.dtos.CreateBookDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@RestController
@RequestMapping("books")
class BookController(
    @Autowired
    val service: BookService
) {
    @GetMapping
    fun getAllBooks(): MutableIterable<Book> = service.getAll()

    @PostMapping
    fun create(@RequestBody createBook: Book ): Book {
       return this.service.create(createBook)
    }
}