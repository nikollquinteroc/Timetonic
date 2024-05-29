package com.example.timetonic.data.model.books

data class Doc(
    val del: Boolean,
    val ext: String,
    val id: Int,
    val internName: String,
    val originName: String,
    val size: Int,
    val type: String,
    val uuid: String
)