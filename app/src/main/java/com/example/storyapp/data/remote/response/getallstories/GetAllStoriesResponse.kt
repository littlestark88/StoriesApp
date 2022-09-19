package com.example.storyapp.data.remote.response.getallstories

import com.example.storyapp.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetAllStoriesResponse(
    @SerializedName("listStory")
    val getAllStoriesItem: List<GetAllStoriesItem>
): BaseResponse()
