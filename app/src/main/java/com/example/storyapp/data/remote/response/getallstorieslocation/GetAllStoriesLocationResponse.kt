package com.example.storyapp.data.remote.response.getallstorieslocation

import com.example.storyapp.base.BaseResponse
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesItem
import com.google.gson.annotations.SerializedName

data class GetAllStoriesLocationResponse(
    @SerializedName("listStory")
    val getAllStoriesLocationItem: List<GetAllStoriesLocationItem>? = null
): BaseResponse()