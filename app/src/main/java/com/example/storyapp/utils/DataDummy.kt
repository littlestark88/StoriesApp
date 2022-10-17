package com.example.storyapp.utils

import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesItem
import com.example.storyapp.data.remote.response.getallstories.GetAllStoriesResponse
import com.example.storyapp.data.remote.response.getallstorieslocation.GetAllStoriesLocationItem
import com.example.storyapp.data.remote.response.getallstorieslocation.GetAllStoriesLocationResponse
import com.example.storyapp.data.remote.response.login.LoginItem
import com.example.storyapp.data.remote.response.login.LoginResponse
import com.example.storyapp.data.remote.response.poststories.PostStoriesResponse
import com.example.storyapp.data.remote.response.register.RegisterResponse
import com.example.storyapp.domain.data.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    fun generateDummyLoginResponse(): Login {
        val listLogin = ListLogin(
            userId = "user-owh6Vym5LXMjuRGU",
            name = "wahyu",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLW93aDZWeW01TFhNanVSR1UiLCJpYXQiOjE2NjQwMDQ5NzV9.5v_eqb2Bbi4wB25ctfdLK2HCu97NaeKFB13Z8n0-MFo"
        )
        return Login(
            listLogin = listLogin,
            error = false,
            message = "success"
        )
    }

    fun generateDummyLoginApiResponse(): LoginResponse {
        val listLogin = LoginItem(
            userId = "user-owh6Vym5LXMjuRGU",
            name = "wahyu",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLW93aDZWeW01TFhNanVSR1UiLCJpYXQiOjE2NjQwMDQ5NzV9.5v_eqb2Bbi4wB25ctfdLK2HCu97NaeKFB13Z8n0-MFo"
        )
        return LoginResponse(
            loginResult = listLogin
        )
    }

    fun generateDummyLoginFailResponse(): Login {
        return Login(
            listLogin = null,
            error = true,
            message = "Invalid password"
        )
    }

    fun generateDummyRegister(): Register {
        return Register(
            error = false,
            message = "User created"
        )
    }

    fun generateDummyRegisterApiResponse(): RegisterResponse {
        return RegisterResponse(
            error = false,
            message = "Created register success"
        )
    }

    fun generateDummyPostStories(): Stories {
        return Stories(
            error = false,
            message = "Created stories success"
        )
    }

    fun generateDummyPostStoriesApiResponse(): PostStoriesResponse {
        return PostStoriesResponse(
            error = false,
            message = "Created stories success"
        )
    }

    fun generateDummyMultipartFile(): MultipartBody.Part {
        val dummyFile = "file"
        return MultipartBody.Part.create(dummyFile.toRequestBody())
    }

    fun generateDummyDescriptionRequestBody(): RequestBody {
        val dummyDescription = "description"
        return dummyDescription.toRequestBody()
    }

    fun generateDummyLatitudeRequestBody(): RequestBody {
        val dummyLatitude = 6.66666F
        return dummyLatitude.toString().toRequestBody()
    }

    fun generateDummyLongitudeRequestBody(): RequestBody {
        val dummyLongitude = 6.66666F
        return dummyLongitude.toString().toRequestBody()
    }

    fun generateDummyGetAllStories(): List<ListGetAllStories> {
        val items: MutableList<ListGetAllStories> = arrayListOf()
            for (i in  1 .. 10) {
                val listGetAllStories = ListGetAllStories(
                    id = "story-FvU4u0Vp2S3PMsFg",
                    photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                    createdAt = "2022-01-08T06:34:18.598Z",
                    name = "Wahyu",
                    description = "Description",
                )
                items.add(listGetAllStories)
            }
        return items
    }

    fun generateDummyGetAllStoriesLocationResponse(): GetAllStoriesLocation {
        return GetAllStoriesLocation(
            listGetStoriesLocation = generateDummyGetAllStoriesLocation(),
            error = false,
            message = "success"
        )
    }

    private fun generateDummyGetAllStoriesLocation(): List<ListGetAllStoriesLocation> {
        val items: MutableList<ListGetAllStoriesLocation> = arrayListOf()
        for (i in  1 .. 10) {
            val listGetAllStoriesLocation = ListGetAllStoriesLocation(
                id = "story-FvU4u0Vp2S3PMsFg",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                createdAt = "2022-01-08T06:34:18.598Z",
                name = "Wahyu",
                description = "Description",
                latitude = -6.1552492,
                longitude = 106.8671625
            )
            items.add(listGetAllStoriesLocation)
        }
        return items
    }

    fun generateDummyGetAllStoriesLocationApiResponse(): GetAllStoriesLocationResponse {
        return GetAllStoriesLocationResponse(
            getAllStoriesLocationItem = generateDummyGetAllStoriesLocationApi()
        )
    }

    private fun generateDummyGetAllStoriesLocationApi(): List<GetAllStoriesLocationItem> {
        val items: MutableList<GetAllStoriesLocationItem> = arrayListOf()
        for (i in  1 .. 10) {
            val listGetAllStoriesLocation = GetAllStoriesLocationItem(
                id = "story-FvU4u0Vp2S3PMsFg",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                createdAt = "2022-01-08T06:34:18.598Z",
                name = "Wahyu",
                description = "Description",
                latitude = -6.1552492,
                longitude = 106.8671625
            )
            items.add(listGetAllStoriesLocation)
        }
        return items
    }

    fun generateDummyGetAllStoriesApiResponse(): GetAllStoriesResponse {
        return GetAllStoriesResponse(
            getAllStoriesItem = generateDummyGetAllStoriesApiItem()
        )
    }

    private fun generateDummyGetAllStoriesApiItem(): List<GetAllStoriesItem> {
        val items: MutableList<GetAllStoriesItem> = arrayListOf()
        for (i in  1 .. 10) {
            val listGetAllStories = GetAllStoriesItem(
                id = "story-FvU4u0Vp2S3PMsFg",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                createdAt = "2022-01-08T06:34:18.598Z",
                name = "Wahyu",
                description = "Description",
            )
            items.add(listGetAllStories)
        }
        return items
    }
}