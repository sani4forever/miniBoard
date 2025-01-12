package com.example.miniboard.retrofit

import com.google.gson.annotations.SerializedName

data class TextItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("text")
    val text: String,

    @SerializedName("parent_id")
    val parentId: Int,

    @SerializedName("utc_created_at")
    val createdAt: Float,

    @SerializedName("comment_depth")
    val commentDepth: Int,

    @SerializedName("comments")
    val comments: List<TextItem>
)