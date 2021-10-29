package com.example.demo.book

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("books")
class BookController {
    @GetMapping
    fun getAllBooks(): String {
        val book: Book = Book(1, "The Nine", "IMAGEM FODA", "Muito legal", false, 5.0, null, null)
        return "${book.name} - ${book.description} -> ${when(book.rating) { null -> 0.0 else -> book.rating}}"
    }
}