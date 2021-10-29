package com.example.demo.book

data class Book(
        val id: Long,
        var name: String,
        var img: String,
        var isRead: Boolean,
        var comments: String
) {
}