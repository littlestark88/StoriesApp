package com.example.storyapp.data.remote.request.register

import com.google.gson.annotations.SerializedName

data class RegisterRequestItem(
    @SerializedName("name")
    val name: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("password")
    val password: String?
)
