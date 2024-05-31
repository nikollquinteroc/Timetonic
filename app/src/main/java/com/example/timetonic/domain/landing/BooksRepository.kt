package com.example.timetonic.domain.landing

import com.example.timetonic.data.model.books.AllBooks
import com.example.timetonic.data.model.books.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getAllBooks(sesskey: String, o_u: String) : Flow<AllBooks>
    suspend fun getBookInfo(sesskey: String, o_u: String, b_c: String) : Flow<Book>
}