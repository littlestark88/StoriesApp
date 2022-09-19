package com.example.storyapp.data.remote.response.login

import com.google.gson.annotations.SerializedName

data class LoginItem(
    @SerializedName("userId")
    val userId: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("token")
    val token: String
)
