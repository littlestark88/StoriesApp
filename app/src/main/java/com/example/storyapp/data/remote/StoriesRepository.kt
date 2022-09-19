package com.example.storyapp.data.remote

import com.example.storyapp.base.ApiResponse
import com.example.storyapp.base.BaseResponse
import com.example.storyapp.data.lib.NetworkBoundResource
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesResponse
import com.example.storyapp.data.remote.response.login.LoginResponse
import com.example.storyapp.domain.IStoriesRepository
import com.example.storyapp.domain.data.response.GetAllStories
import com.example.storyapp.domain.data.response.Login
import com.example.storyapp.domain.data.response.Register
import com.example.storyapp.domain.data.response.Stories
import com.example.storyapp.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoriesRepository(
    private val remoteDataSource: RemoteDataSource
): IStoriesRepository {

    override suspend fun postRegister(registerRequestItem: RegisterRequestItem): Flow<Resource<Register>> {
        return object : NetworkBoundResource<Register, BaseResponse>() {
            override fun fetchFromNetwork(data: BaseResponse?): Flow<Register> {
                return flowOf(data).map { DataMapper.mapRegisterToDomain(data) }
            }

            override suspend fun createCall(): Flow<ApiResponse<BaseResponse>> {
                return remoteDataSource.postRegister(registerRequestItem)
            }
        }.asFlow()
    }

    override suspend fun postStories(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<Resource<Stories>> {
        return object : NetworkBoundResource<Stories, BaseResponse>() {
            override fun fetchFromNetwork(data: BaseResponse?): Flow<Stories> {
                return flowOf(data).map { DataMapper.mapStoriesToDomain(data) }
            }

            override suspend fun createCall(): Flow<ApiResponse<BaseResponse>> {
                return remoteDataSource.postStories(token, file, description)
            }
        }.asFlow()
    }

    override suspend fun postLogin(loginRequestItem: LoginRequestItem): Flow<Resource<Login>> {
        return object : NetworkBoundResource<Login, LoginResponse>() {
            override fun fetchFromNetwork(data: LoginResponse?): Flow<Login> {
                return flowOf(data).map { DataMapper.mapLoginToDomain(data) }
            }

            override suspend fun createCall(): Flow<ApiResponse<LoginResponse>> {
                return remoteDataSource.postLogin(loginRequestItem)
            }
        }.asFlow()
    }

    override suspend fun getStories(token: String): Flow<Resource<GetAllStories>> {
        return object : NetworkBoundResource<GetAllStories, GetAllStoriesResponse>() {
            override fun fetchFromNetwork(data: GetAllStoriesResponse?): Flow<GetAllStories> {
                return flowOf(data).map { DataMapper.mapGetStoriesToDomain(data) }
            }

            override suspend fun createCall(): Flow<ApiResponse<GetAllStoriesResponse>> {
                return remoteDataSource.getStories(token)
            }
        }.asFlow()
    }
}