package com.example.storyapp.domain

import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.data.response.GetAllStories
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoriesInteractor(private val storiesRepository: IStoriesRepository): IStoriesUseCase {
    override suspend fun postRegister(registerRequestItem: RegisterRequestItem) = storiesRepository.postRegister(registerRequestItem)
    override suspend fun postStories(token: String, file: MultipartBody.Part, description: RequestBody) = storiesRepository.postStories(token, file, description)
    override suspend fun postLogin(loginRequestItem: LoginRequestItem) = storiesRepository.postLogin(loginRequestItem)
    override suspend fun getStories(token: String): Flow<Resource<GetAllStories>> = storiesRepository.getStories(token)
}