package com.example.homework_retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface ReqresApi {

    @POST("users")
    fun postUsers(
        @Body user:User
    ): Call<User>
}