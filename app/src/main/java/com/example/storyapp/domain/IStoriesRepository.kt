package com.example.storyapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.poststories.PostStoriesRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.data.response.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IStoriesRepository {
    suspend fun postRegister(registerRequestItem: RegisterRequestItem): Flow<Resource<Register>>
    suspend fun postStories(token: String, file: MultipartBody.Part, postStoriesRequestItem: PostStoriesRequestItem): Flow<Resource<Stories>>
    suspend fun postLogin(loginRequestItem: LoginRequestItem): Flow<Resource<Login>>
//    suspend fun getStories(token: String): Flow<Resource<GetAllStories>>
    fun getAllStories(token: String): Flow<PagingData<ListGetAllStories>>
}