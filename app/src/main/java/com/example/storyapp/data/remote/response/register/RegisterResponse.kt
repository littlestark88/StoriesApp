package com.example.storyapp.data.remote.response.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("error")
    val error: Boolean = false,

    @SerializedName("message")
    val message: String? = null
)
