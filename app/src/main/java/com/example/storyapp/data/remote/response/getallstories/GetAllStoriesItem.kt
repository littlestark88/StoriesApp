package com.example.storyapp.data.remote.response.getallstories

import com.google.gson.annotations.SerializedName

data class GetAllStoriesItem(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("photoUrl")
    var photoUrl: String,

    @SerializedName("createdAt")
    val createdAt: String?
)