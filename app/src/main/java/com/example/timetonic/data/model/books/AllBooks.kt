package com.example.timetonic.data.model.books

data class AllBooks(
    val books: List<Book>,
    val contacts: List<Any>,
    val nbBooks: Int,
    val nbContacts: Int
)