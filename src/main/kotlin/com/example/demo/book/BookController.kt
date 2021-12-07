package com.example.demo.book

import com.example.demo.book.dtos.CreateBookDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/books")
class BookController(
    @Autowired
    val service: BookService
) {
    @GetMapping
    fun getAllBooks(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "linesPerPage", defaultValue = "5") linesPerPage: Int,
        @RequestParam(value = "direction", defaultValue = "ASC") direction: String,
        @RequestParam(value = "orderBy", defaultValue = "id") orderBy: String
    ): Page<Book> {
        val pageRequest: PageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)
        return service.getAll(pageRequest)
    }

    @GetMapping("/{bookId}")
    fun findOne(@PathVariable bookId: Long): Book = service.getOne(bookId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createBook: CreateBookDto ): Book {
        return this.service.create(createBook)
    }

    @DeleteMapping("/{bookId}")
    fun delete(@PathVariable bookId: Long) {
        return this.service.delete(bookId)
    }

    @PutMapping("/{bookId}")
    fun update(@PathVariable bookId: Long, @RequestBody updateBook: Book): Book {
        return this.service.update(bookId, updateBook)
    }

    @GetMapping("/user")
    fun getBooksFromUser(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "linesPerPage", defaultValue = "5") linesPerPage: Int,
        @RequestParam(value = "direction", defaultValue = "ASC") direction: String,
        @RequestParam(value = "orderBy", defaultValue = "id") orderBy: String,
        @RequestParam(value = "userId", defaultValue = "0") userId: Long
    ): Page<Book> {
        val pageRequest: PageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)
        return service.getByUserId(pageRequest, userId)
    }
}