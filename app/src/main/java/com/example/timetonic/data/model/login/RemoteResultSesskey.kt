package com.example.timetonic.data.model.login

data class RemoteResultSesskey(
    val appName: String,
    val createdVNB: String,
    val id: String,
    val req: String,
    val restrictions: Restrictions,
    val sesskey: String,
    val status: String
)