package com.example.storyapp.data.remote

import android.util.Log
import com.example.storyapp.base.ApiResponse
import com.example.storyapp.data.remote.network.ApiService
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.data.remote.response.getallstorieslocation.GetAllStoriesLocationResponse
import com.example.storyapp.data.remote.response.login.LoginResponse
import com.example.storyapp.data.remote.response.poststories.PostStoriesResponse
import com.example.storyapp.data.remote.response.register.RegisterResponse
import com.example.storyapp.utils.SharePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource(
    private val apiService: ApiService,
    private val sharePreferences: SharePreferences
) {

    suspend fun postRegister(registerRequestItem: RegisterRequestItem): Flow<ApiResponse<RegisterResponse>> {
        return flow {
            try {
                val data = apiService.postRegister(registerRequestItem)
                if (data.message?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("remoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postStories(
        token: String,
        fileImage: MultipartBody.Part,
        description: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody
    ): Flow<ApiResponse<PostStoriesResponse>> {
        return flow {
            try {
                val data =
                    apiService.postStories(token, fileImage, description, latitude, longitude)
                if (data.message?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postLogin(loginRequestItem: LoginRequestItem): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val data = apiService.postLogin(loginRequestItem)
                if (data.message?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(data))
                    sharePreferences.saveToken("Bearer ${data.loginResult?.token.toString()}")
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("remoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMapStories(token: String): Flow<ApiResponse<GetAllStoriesLocationResponse>> {
        return flow {
            try {
                val data = apiService.getMapStories(token)
                if (data.message?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("remoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}