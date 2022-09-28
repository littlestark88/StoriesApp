package com.example.storyapp.data.remote.response.getallstorieslocation

import com.google.gson.annotations.SerializedName

data class GetAllStoriesLocationItem(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("photoUrl")
    var photoUrl: String,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("lat")
    val latitude: Double?,

    @SerializedName("lon")
    val longitude: Double?
)
