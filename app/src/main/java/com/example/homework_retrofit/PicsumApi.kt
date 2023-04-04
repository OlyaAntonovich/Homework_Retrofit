package com.example.homework_retrofit

import retrofit2.Call
import retrofit2.http.*

interface PicsumApi {

    @GET("list?page=2&limit=50")
    fun getItems(
    ): Call<List<Item>>


}