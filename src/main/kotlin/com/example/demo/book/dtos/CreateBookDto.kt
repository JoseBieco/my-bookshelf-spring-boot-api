package com.example.demo.book.dtos

import java.time.LocalDate

class CreateBookDto(
    var name: String? = null,
    var image: String? = null,
    var description: String? = null,
    var isRead: Boolean? = null,
    var rating: Double? = null,
    var purchaseDate: LocalDate? = null,
    var completionDate: LocalDate? = null
) {
}