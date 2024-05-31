package com.example.timetonic.data.repository.login

import com.example.timetonic.data.RetrofitService
import com.example.timetonic.data.model.login.LoginResponse
import com.example.timetonic.domain.login.LoginRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LoginRepositoryImpl(private val service: RetrofitService) : LoginRepository {
    override suspend fun login(username: String, password: String): Flow<LoginResponse> {
        val loginResponse = coroutineScope {
            val resultAppKey = async { service.createAppKey() }
            val dataAppKey = resultAppKey.await()

            val resultOauthKey = async {
                service.createOauthKey(
                    login = username,
                    pwd = password,
                    appKey = dataAppKey.appkey
                )
            }
            val dataOauthKey = resultOauthKey.await()

            val resultSessKey = async {
                service.createSessKey(
                    o_u = dataOauthKey.o_u,
                    u_c = dataOauthKey.o_u,
                    oauthkey = dataOauthKey.oauthkey
                )
            }
            val dataSessKey = resultSessKey.await()

            return@coroutineScope LoginResponse(
                o_u = dataOauthKey.o_u,
                sesskey = dataSessKey.sesskey
            )
        }

        return flowOf(loginResponse)
    }
}