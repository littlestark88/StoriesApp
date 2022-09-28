package com.example.storyapp.domain.data.response

data class GetAllStoriesLocation(
    val error: Boolean,
    val message: String,
    val listGetStoriesLocation: List<ListGetAllStoriesLocation>
)
