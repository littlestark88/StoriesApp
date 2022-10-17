package com.example.storyapp.domain

import androidx.paging.PagingData
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.data.response.GetAllStoriesLocation
import com.example.storyapp.domain.data.response.ListGetAllStories
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoriesInteractor(private val storiesRepository: IStoriesRepository) : IStoriesUseCase {
    override suspend fun postRegister(registerRequestItem: RegisterRequestItem) =
        storiesRepository.postRegister(registerRequestItem)

    override suspend fun postStories(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody
    ) = storiesRepository.postStories(token, file, description, latitude, longitude)

    override suspend fun postLogin(loginRequestItem: LoginRequestItem) =
        storiesRepository.postLogin(loginRequestItem)

    override fun getMapStories(token: String): Flow<Resource<GetAllStoriesLocation>> =
        storiesRepository.getMapStories(token)


    override fun getAllStories(token: String): Flow<PagingData<ListGetAllStories>> =
        storiesRepository.getAllStories(token)
}