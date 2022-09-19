package com.example.storyapp.domain.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListGetAllStories(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String
): Parcelable
