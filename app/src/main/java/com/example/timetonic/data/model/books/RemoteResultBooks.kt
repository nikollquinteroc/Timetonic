package com.example.timetonic.data.model.books

data class RemoteResultBooks(
    val allBooks: AllBooks,
    val createdVNB: String,
    val req: String,
    val sstamp: Long,
    val status: String
)