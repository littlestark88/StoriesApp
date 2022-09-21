package com.example.storyapp.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.*
import com.example.storyapp.base.ApiResponse
import com.example.storyapp.base.BaseResponse
import com.example.storyapp.data.StoriesRemoteMediator
import com.example.storyapp.data.lib.NetworkBoundResource
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.local.StoriesDatabase
import com.example.storyapp.data.remote.network.ApiService
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.poststories.PostStoriesRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.data.remote.response.login.LoginResponse
import com.example.storyapp.domain.IStoriesRepository
import com.example.storyapp.domain.data.response.ListGetAllStories
import com.example.storyapp.domain.data.response.Login
import com.example.storyapp.domain.data.response.Register
import com.example.storyapp.domain.data.response.Stories
import com.example.storyapp.utils.DataMapper
import com.example.storyapp.utils.SharePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoriesRepository(
    private val databaseStories: StoriesDatabase,
    private val apiService: ApiService,
    private val sharePreferences: SharePreferences,
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
        postStoriesRequestItem: PostStoriesRequestItem
    ): Flow<Resource<Stories>> {
        return object : NetworkBoundResource<Stories, BaseResponse>() {
            override fun fetchFromNetwork(data: BaseResponse?): Flow<Stories> {
                return flowOf(data).map { DataMapper.mapStoriesToDomain(data) }
            }

            override suspend fun createCall(): Flow<ApiResponse<BaseResponse>> {
                return remoteDataSource.postStories(token, file, postStoriesRequestItem)
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

    //    override suspend fun getStories(token: String): Flow<Resource<GetAllStories>> {
//        return object : NetworkBoundResource<GetAllStories, GetAllStoriesResponse>() {
//            override fun fetchFromNetwork(data: GetAllStoriesResponse?): Flow<GetAllStories> {
//                return flowOf(data).map { DataMapper.mapGetStoriesToDomain(data) }
//            }
//
//            override suspend fun createCall(): Flow<ApiResponse<GetAllStoriesResponse>> {
////                return remoteDataSource.getStories(token)
//            }
//        }.asFlow()
//    }
    override fun getAllStories(token: String): Flow<PagingData<ListGetAllStories>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = StoriesRemoteMediator(databaseStories,apiService,
                sharePreferences.getToken().toString()
            ),
            pagingSourceFactory = {
                databaseStories.getAllStoriesDao().getAllStories()
            }
        ).flow.map{
            DataMapper.mapGetStoriesPaging(it)

        }
    }


}