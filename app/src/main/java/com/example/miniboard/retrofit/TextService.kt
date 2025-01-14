package com.example.miniboard.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface TextService {
    @Headers("Content-Type: application/json")
    @GET("texts")
    suspend fun getTexts() : Response<Texts>

    @GET("texts/{text_id}")
    suspend fun getTextsById(@Path("text_id") id: Int): Response<TextItem>
}