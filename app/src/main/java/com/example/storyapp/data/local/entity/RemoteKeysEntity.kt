package com.example.storyapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RemoteKeysTable")
data class RemoteKeysEntity (
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("prevKey")
    val prevKey: Int?,

    @SerializedName("nextKey")
    val nextKey: Int?
)