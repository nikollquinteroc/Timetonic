package com.example.timetonic.data.model.login
data class Restrictions(
    val carnet_code: Any,
    val carnet_owner: Any,
    val hideEvents: Boolean,
    val hideMessages: Boolean,
    val hideTables: Boolean,
    val `internal`: Boolean,
    val readonly: Boolean
)