package com.example.storyapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.poststories.PostStoriesRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.data.response.ListGetAllStories
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class StoriesInteractor(private val storiesRepository: IStoriesRepository) : IStoriesUseCase {
    override suspend fun postRegister(registerRequestItem: RegisterRequestItem) =
        storiesRepository.postRegister(registerRequestItem)

    override suspend fun postStories(
        token: String,
        file: MultipartBody.Part,
        postStoriesRequestItem: PostStoriesRequestItem
    ) = storiesRepository.postStories(token, file, postStoriesRequestItem)

    override suspend fun postLogin(loginRequestItem: LoginRequestItem) =
        storiesRepository.postLogin(loginRequestItem)

    //    override suspend fun getStories(token: String): Flow<Resource<GetAllStories>> = storiesRepository.getStories(token)
    override fun getAllStories(token: String): Flow<PagingData<ListGetAllStories>> =
        storiesRepository.getAllStories(token)
}