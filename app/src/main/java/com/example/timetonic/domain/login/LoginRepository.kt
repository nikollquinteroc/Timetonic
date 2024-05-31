package com.example.timetonic.domain.login

import com.example.timetonic.data.model.login.LoginResponse
import com.example.timetonic.data.model.login.RemoteResultAppkey
import com.example.timetonic.data.model.login.RemoteResultOauthkey
import com.example.timetonic.data.model.login.RemoteResultSesskey
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(username: String, password: String): Flow<LoginResponse>
}


