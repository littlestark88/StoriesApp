package com.example.storyapp.data.remote.response.poststories

import com.google.gson.annotations.SerializedName

data class PostStoriesResponse(
    @SerializedName("error")
    val error: Boolean = false,

    @SerializedName("message")
    val message: String? = null
)
