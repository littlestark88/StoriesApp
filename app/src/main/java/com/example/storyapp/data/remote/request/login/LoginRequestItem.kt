package com.example.storyapp.data.remote.request.login

import com.google.gson.annotations.SerializedName

data class LoginRequestItem(
    @SerializedName("email")
    val email: String?,

    @SerializedName("password")
    val password: String?
)
