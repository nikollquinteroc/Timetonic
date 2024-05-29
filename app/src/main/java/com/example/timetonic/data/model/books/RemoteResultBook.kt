package com.example.timetonic.data.model.books

data class RemoteResultBook(
    val status: String,
    val sstamp: String,
    val book: Book,
    val createdVNB: String,
    val req: String
)
