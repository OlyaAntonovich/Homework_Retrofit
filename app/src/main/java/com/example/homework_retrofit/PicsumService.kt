package com.example.homework_retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object PicsumService {
    private const val BASE_URL = "https://picsum.photos/v2/"
    val api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create<PicsumApi>()
    }
}