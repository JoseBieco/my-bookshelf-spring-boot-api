package com.example.demo.book

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("books")
class BookController {
    @GetMapping
    fun getAllBooks(): String {
        return "Hello Kotlin"
    }
}