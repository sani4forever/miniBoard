package com.example.miniboard

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TextService {
    @Headers("Content-Type: application/json")
    @GET("texts")
    suspend fun getTexts() : Response<Texts>
}