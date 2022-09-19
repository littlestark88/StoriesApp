package com.example.storyapp.presentasion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.IStoriesUseCase
import com.example.storyapp.domain.data.response.GetAllStories
import com.example.storyapp.domain.data.response.Login
import com.example.storyapp.domain.data.response.Register
import com.example.storyapp.domain.data.response.Stories
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoriesViewModel(
    private val storiesUseCase: IStoriesUseCase,
): ViewModel() {

    val postRegister: LiveData<Resource<Register>> by lazy { _postRegister }
    private val _postRegister = MutableLiveData<Resource<Register>>()

    val postStories: LiveData<Resource<Stories>> by lazy { _postStories }
    private val _postStories = MutableLiveData<Resource<Stories>>()

    val postLogin: LiveData<Resource<Login>> by lazy { _postLogin }
    private val _postLogin = MutableLiveData<Resource<Login>>()

    val getStories: LiveData<Resource<GetAllStories>> by lazy { _getStories }
    private val _getStories = MutableLiveData<Resource<GetAllStories>>()



    fun postRegister(registerRequestItem: RegisterRequestItem) =
        viewModelScope.launch {
            storiesUseCase.postRegister(registerRequestItem).collect {
                _postRegister.value = it
            }
        }

    fun postStories(token: String, file: MultipartBody.Part, description: RequestBody) =
        viewModelScope.launch {
            storiesUseCase.postStories(token, file, description).collect {
                _postStories.value = it
            }
        }

    fun postLogin(loginRequestItem: LoginRequestItem) =
        viewModelScope.launch {
            storiesUseCase.postLogin(loginRequestItem).collect {
                _postLogin.value = it
            }
        }

    fun getStories(token: String) =
        viewModelScope.launch {
            storiesUseCase.getStories(token).collect {
                if(it.data?.listGetStories?.isEmpty() == true) {
                    _getStories.value = Resource.Empty()
                } else {
                    _getStories.value = it
                }

            }
        }
}