package com.example.storyapp.domain.data.response

data class ListGetAllStoriesLocation(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val latitude: Double,
    val longitude: Double
)
