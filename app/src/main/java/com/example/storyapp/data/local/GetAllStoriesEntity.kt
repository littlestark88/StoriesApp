package com.example.storyapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "GetAllStoriesTable")
data class GetAllStoriesEntity(

    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("photoUrl")
    var photoUrl: String,

    @SerializedName("createdAt")
    val createdAt: String?
)
