package com.example.storyapp.data.remote.response.login

import com.example.storyapp.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("loginResult")
    val loginResult: LoginItem?
): BaseResponse()
