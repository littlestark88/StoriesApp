package com.example.storyapp.domain.data.response

data class Login(
    val error: Boolean,
    val message: String,
    val listLogin: ListLogin
)