package com.example.storyapp.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.example.storyapp.data.local.entity.GetAllStoriesEntity
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesItem
import com.example.storyapp.data.remote.response.getallstorieslocation.GetAllStoriesLocationItem
import com.example.storyapp.data.remote.response.getallstorieslocation.GetAllStoriesLocationResponse
import com.example.storyapp.data.remote.response.login.LoginItem
import com.example.storyapp.data.remote.response.login.LoginResponse
import com.example.storyapp.data.remote.response.poststories.PostStoriesResponse
import com.example.storyapp.data.remote.response.register.RegisterResponse
import com.example.storyapp.domain.data.response.*

object DataMapper {
    fun mapRegisterToDomain(response: RegisterResponse?): Register {
        return Register(
            error = response?.error ?: false,
            message = response?.message.orEmpty()
        )
    }

    fun mapStoriesToDomain(response: PostStoriesResponse?): Stories {
        return Stories(
            error = response?.error ?: false,
            message = response?.message.orEmpty()
        )
    }

    fun mapLoginToDomain(response: LoginResponse?): Login {
        return Login(
            error = response?.error ?: false,
            message = response?.message.orEmpty(),
            listLogin = mapListLogin(response?.loginResult)
        )
    }

    private fun mapListLogin(listData: LoginItem?): ListLogin {
        return ListLogin(
            userId = listData?.userId.orEmpty(),
            name = listData?.name.orEmpty(),
            token = listData?.token.orEmpty()
        )
    }

    fun mapGetStoriesEntity(listData: List<GetAllStoriesItem>?): List<GetAllStoriesEntity> =
        listData?.map {
            GetAllStoriesEntity(
                id = it.id.orEmpty(),
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                photoUrl = it.photoUrl,
                createdAt = it.createdAt.orEmpty()
            )
        } ?: emptyList()

    fun mapGetStoriesPaging(listData: PagingData<GetAllStoriesEntity>): PagingData<ListGetAllStories> =
        listData.map {
            ListGetAllStories(
                id = it.id,
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                photoUrl = it.photoUrl,
                createdAt = it.createdAt.orEmpty()
            )
        }

    fun mapGetStoriesWitLocation(data : GetAllStoriesLocationResponse?): GetAllStoriesLocation {
        return GetAllStoriesLocation(
            error = data?.error ?: false,
            message = data?.message.orEmpty(),
            listGetStoriesLocation = mapListStoriesLocation(data?.getAllStoriesLocationItem)
        )
    }

    private fun mapListStoriesLocation(data: List<GetAllStoriesLocationItem>?): List<ListGetAllStoriesLocation> =
        data?.map {
            ListGetAllStoriesLocation(
                id = it.id.orEmpty(),
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                photoUrl = it.photoUrl,
                createdAt = it.createdAt.orEmpty(),
                latitude = it.latitude ?: 0.0,
                longitude = it.longitude ?: 0.0
            )
        } ?: emptyList()

}


