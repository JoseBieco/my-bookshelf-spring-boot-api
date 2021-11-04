package com.example.demo.book

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("books")
class BookController(
    @Autowired
    val service: BookService
) {
    @GetMapping
    fun getAllBooks(): MutableIterable<Book> = service.getAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createBook: Book ): Book {
        return this.service.create(createBook)
    }
}