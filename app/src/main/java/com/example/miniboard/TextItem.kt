package com.example.miniboard

import com.google.gson.annotations.SerializedName

data class TextItem(
    @SerializedName("id")
    val id : Int,
    @SerializedName("username")
    val username : String,
    @SerializedName("text")
    val text : String,
    @SerializedName("created_at_utc")
    val createdAt : String
)