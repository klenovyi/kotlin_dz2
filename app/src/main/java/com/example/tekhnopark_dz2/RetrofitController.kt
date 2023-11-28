package com.example.tekhnopark_dz2package


import com.example.tekhnopark_dz2.DataObject
import com.example.tekhnopark_dz2.GifsApi
import com.example.tekhnopark_dz2.OriginalImage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class RetrofitController() {
    private val API_KEY: String = "m4DVBI4nGyPNnBKyQ9STrK61BaV5iN1q"
    private val BASE_URL: String = "https://api.giphy.com/v1/"
    private var offset = 0

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create<GifsApi>(GifsApi::class.java)

    suspend fun requestGifs(): MutableList<DataObject> {
        val response = api.getAll(API_KEY, offset)
        if (response.isSuccessful) {
            offset += 50
            val gifs = response.body()?.gifs ?: throw IOException("response is not succesful")
            return  gifs
        } else {
            throw IOException("response is not succesful")
        }
    }
}