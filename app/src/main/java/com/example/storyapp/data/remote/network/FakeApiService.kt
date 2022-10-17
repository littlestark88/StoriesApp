package com.example.storyapp.data.remote.network

import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesResponse
import com.example.storyapp.data.remote.response.getallstorieslocation.GetAllStoriesLocationResponse
import com.example.storyapp.data.remote.response.login.LoginResponse
import com.example.storyapp.data.remote.response.poststories.PostStoriesResponse
import com.example.storyapp.data.remote.response.register.RegisterResponse
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.DataDummy.generateDummyGetAllStoriesApiResponse
import com.example.storyapp.utils.DataDummy.generateDummyGetAllStoriesLocationApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService: ApiService {

    private val dummyLoginResponse = DataDummy.generateDummyLoginApiResponse()
    private val dummyRegisterResponse = DataDummy.generateDummyRegisterApiResponse()
    private val dummyPostStoriesResponse = DataDummy.generateDummyPostStoriesApiResponse()
    private val dummyGetAllStoriesLocation = generateDummyGetAllStoriesLocationApiResponse()
    private val dummyGetAllStories = generateDummyGetAllStoriesApiResponse()

    override suspend fun postRegister(registerRequestItem: RegisterRequestItem): RegisterResponse {
        return dummyRegisterResponse
    }

    override suspend fun postLogin(loginRequestItem: LoginRequestItem): LoginResponse {
        return dummyLoginResponse
    }

    override suspend fun postStories(
        auth: String,
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody
    ): PostStoriesResponse {
        return dummyPostStoriesResponse
    }

    override suspend fun getAllStories(
        auth: String,
        size: Int?,
        page: Int?,
        location: Int?
    ): GetAllStoriesResponse {
        return dummyGetAllStories
    }

    override suspend fun getMapStories(
        auth: String,
        location: Int?
    ): GetAllStoriesLocationResponse {
        return dummyGetAllStoriesLocation
    }


}