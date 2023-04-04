package com.example.homework_retrofit

import com.google.gson.annotations.SerializedName

data class Item(
    val id: Int,
    val author: String,
    @SerializedName("download_url")
    val avatar: String
) {
}