package com.example.tekhnopark_dz2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsApi {
    @GET("gifs/trending")
    suspend fun getAll(@Query("api_key") api_key : String, @Query("offset") offset: Int): Response<GifsModel>
}