package com.example.storyapp.data.remote.network

import com.example.storyapp.base.BaseResponse
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesResponse
import com.example.storyapp.data.remote.response.login.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Part("description") description : RequestBody,
        @Part("lat") latitude: RequestBody?,
        @Part("lon") longitude: RequestBody?
    ): BaseResponse

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") auth: String,
        @Query("size") size: Int? = 0,
        @Query("page") page: Int? = 0,
        @Query("location") location: Int? = 1
    ): GetAllStoriesResponse

}