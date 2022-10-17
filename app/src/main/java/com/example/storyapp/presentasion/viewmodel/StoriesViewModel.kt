package com.example.storyapp.presentasion.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.IStoriesUseCase
import com.example.storyapp.domain.data.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoriesViewModel(
    private val storiesUseCase: IStoriesUseCase,
) : ViewModel() {

    val postRegister: LiveData<Resource<Register>> by lazy { _postRegister }
    private val _postRegister = MutableLiveData<Resource<Register>>()

    val postStories: LiveData<Resource<Stories>> by lazy { _postStories }
    private val _postStories = MutableLiveData<Resource<Stories>>()

    val postLogin: LiveData<Resource<Login>> by lazy { _postLogin }
    private val _postLogin = MutableLiveData<Resource<Login>>()

    val getMapStories: LiveData<Resource<GetAllStoriesLocation>> by lazy { _getMapStories }
    private val _getMapStories = MutableLiveData<Resource<GetAllStoriesLocation>>()

    fun postRegister(registerRequestItem: RegisterRequestItem): Flow<Resource<Register>> {
        _postRegister.value = Resource.Loading()

        viewModelScope.launch {
            storiesUseCase.postRegister(registerRequestItem).collect {
                _postRegister.value = it
            }
        }
        return _postRegister.asFlow()
    }

    fun postStories(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody
    ): Flow<Resource<Stories>> {
        _postRegister.value = Resource.Loading()
            viewModelScope.launch {
             storiesUseCase.postStories(token, file, description, latitude, longitude).collect {
                _postStories.value = it
                }
            }
        return _postStories.asFlow()
    }


    fun postLogin(loginRequestItem: LoginRequestItem): Flow<Resource<Login>> {
        _postLogin.value = Resource.Loading()
        viewModelScope.launch {
            storiesUseCase.postLogin(loginRequestItem).collect {
                _postLogin.value = it
            }
        }
        return _postLogin.asFlow()
    }

    fun getMapStories(token: String): Flow<Resource<GetAllStoriesLocation>> {
        _getMapStories.value = Resource.Loading()
        viewModelScope.launch {
            storiesUseCase.getMapStories(token).collect {
                _getMapStories.value = it
            }
        }
        return _getMapStories.asFlow()
    }

    fun getStories(token: String): LiveData<PagingData<ListGetAllStories>> =
        storiesUseCase.getAllStories(token).cachedIn(viewModelScope).asLiveData()


}
