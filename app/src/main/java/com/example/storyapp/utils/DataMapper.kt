package com.example.storyapp.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.example.storyapp.base.BaseResponse
import com.example.storyapp.data.local.GetAllStoriesEntity
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesItem
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesResponse
import com.example.storyapp.data.remote.response.login.LoginItem
import com.example.storyapp.data.remote.response.login.LoginResponse
import com.example.storyapp.domain.data.response.*

object DataMapper {
    fun mapRegisterToDomain(response: BaseResponse?): Register {
        return Register(
            error = response?.error ?: false,
            message = response?.message.orEmpty()
        )
    }

    fun mapStoriesToDomain(response: BaseResponse?): Stories {
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

    fun mapGetStoriesToDomain(response: GetAllStoriesResponse?): GetAllStories {
        return GetAllStories(
            error = response?.error ?: false,
            message = response?.message.orEmpty(),
            listGetStories = mapListGetStories(response?.getAllStoriesItem)
        )
    }

    private fun mapListGetStories(listData: List<GetAllStoriesItem>?): List<ListGetAllStories> =
        listData?.map {
            ListGetAllStories(
                id = it.id.orEmpty(),
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                photoUrl = it.photoUrl,
                createdAt = it.createdAt.orEmpty()
            )
        } ?: emptyList()

    fun mapGetStoriesEntity(listData: List<GetAllStoriesItem>): List<GetAllStoriesEntity> =
        listData.map {
            GetAllStoriesEntity(
                id = it.id.orEmpty(),
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                photoUrl = it.photoUrl,
                createdAt = it.createdAt.orEmpty()
            )
        }

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
}


