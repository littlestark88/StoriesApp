package com.example.storyapp.data.remote.request.poststories

import com.google.gson.annotations.SerializedName

data class PostStoriesRequestItem(
    @SerializedName("description")
    val description: String?,

    @SerializedName("lat")
    val latitude: Float?,

    @SerializedName("lon")
    val longitude: Float?
)
