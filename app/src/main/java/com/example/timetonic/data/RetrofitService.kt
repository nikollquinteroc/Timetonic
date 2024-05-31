package com.example.timetonic.data

import com.example.timetonic.data.model.books.RemoteResultBook
import com.example.timetonic.data.model.books.RemoteResultBooks
import com.example.timetonic.data.model.login.RemoteResultAppkey
import com.example.timetonic.data.model.login.RemoteResultOauthkey
import com.example.timetonic.data.model.login.RemoteResultSesskey
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

const val VERSION = "1.47"
const val APP_NAME = "android"
const val REQ_FOR_APP_KEY = "createAppkey"
const val REQ_FOR_OAUTH_KEY = "createOauthkey"
const val REQ_FOR_SESS_KEY = "createSesskey"
const val REQ_FOR_GET_ALL_BOOKS = "getAllBooks"
const val REQ_FOR_GET_BOOK_INFO = "getBookInfo"

const val BASE_URL = "https://timetonic.com/live/api.php/"
const val BASE_URL_FOR_IMAGES = "https://timetonic.com"

interface RetrofitService {
    @POST("createAppkey")
    suspend fun createAppKey(
        @Query("version") version: String = VERSION,
        @Query("req") req: String = REQ_FOR_APP_KEY,
        @Query("appname") appname: String = APP_NAME
    ): RemoteResultAppkey

    @POST("createOauthkey")
    suspend fun createOauthKey(
        @Query("version") version: String = VERSION,
        @Query("req") req: String = REQ_FOR_OAUTH_KEY,
        @Query("login") login: String,
        @Query("pwd") pwd: String,
        @Query("appkey") appKey: String
    ): RemoteResultOauthkey

    @POST("createSesskey")
    suspend fun createSessKey(
        @Query("version") version: String = VERSION,
        @Query("req") req: String = REQ_FOR_SESS_KEY,
        @Query("o_u") o_u: String,
        @Query("u_c") u_c: String,
        @Query("oauthkey") oauthkey: String
    ): RemoteResultSesskey

    @POST("getAllBooks")
    suspend fun getAllBooks(
        @Query("version") version: String = VERSION,
        @Query("req") req: String = REQ_FOR_GET_ALL_BOOKS,
        @Query("u_c") u_c: String,
        @Query("o_u") o_u: String,
        @Query("sesskey") sesskey: String
    ): RemoteResultBooks

    @POST("getBookInfo")
    suspend fun getBookInfo(
        @Query("version") version: String = VERSION,
        @Query("req") req: String = REQ_FOR_GET_BOOK_INFO,
        @Query("u_c") u_c: String,
        @Query("o_u") o_u: String,
        @Query("sesskey") sesskey: String,
        @Query("b_c") b_c: String,
        @Query("b_o") b_o: String
    ): RemoteResultBook
}

object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}