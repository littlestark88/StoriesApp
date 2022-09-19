package com.example.storyapp.domain

import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.data.response.GetAllStories
import com.example.storyapp.domain.data.response.Login
import com.example.storyapp.domain.data.response.Register
import com.example.storyapp.domain.data.response.Stories
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IStoriesUseCase {
    suspend fun postRegister(registerRequestItem: RegisterRequestItem): Flow<Resource<Register>>
    suspend fun postStories(token: String, file: MultipartBody.Part, description: RequestBody): Flow<Resource<Stories>>
    suspend fun postLogin(loginRequestItem: LoginRequestItem): Flow<Resource<Login>>
    suspend fun getStories(token: String): Flow<Resource<GetAllStories>>
}