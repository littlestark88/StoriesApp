package com.example.storyapp.data.remote.network

import com.example.storyapp.base.BaseResponse
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.poststories.PostStoriesRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesItem
import com.example.storyapp.data.remote.response.login.LoginResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun postRegister(@Body registerRequestItem: RegisterRequestItem): BaseResponse

    @POST("login")
    suspend fun postLogin(@Body loginRequestItem: LoginRequestItem): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun postStories(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Body postStoriesRequestItem: PostStoriesRequestItem
    ): BaseResponse

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") auth: String,
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("location") location: Int
    ): List<GetAllStoriesItem>

}