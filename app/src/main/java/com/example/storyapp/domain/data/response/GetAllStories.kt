package com.example.storyapp.domain.data.response

data class GetAllStories(
    val error: Boolean,
    val message: String,
    val listGetStories: List<ListGetAllStories>
)
