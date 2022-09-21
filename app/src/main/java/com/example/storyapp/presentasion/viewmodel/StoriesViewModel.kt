package com.example.storyapp.presentasion.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.poststories.PostStoriesRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.IStoriesUseCase
import com.example.storyapp.domain.data.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
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

//    val getStories: LiveData<PagingData<ListGetAllStories>> by lazy { _getStories }
//    private val _getStories = MutableLiveData<PagingData<ListGetAllStories>>()

    fun postRegister(registerRequestItem: RegisterRequestItem) =
        viewModelScope.launch {
            storiesUseCase.postRegister(registerRequestItem).collect {
                _postRegister.value = it
            }
        }

    fun postStories(token: String, file: MultipartBody.Part, postStoriesRequestItem: PostStoriesRequestItem) =
        viewModelScope.launch {
            storiesUseCase.postStories(token, file, postStoriesRequestItem).collect {
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
        storiesUseCase.getAllStories(token).cachedIn(viewModelScope).asLiveData()
}