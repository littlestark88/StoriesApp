package com.example.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.data.remote.network.ApiService
import com.example.storyapp.data.remote.network.FakeApiService
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.presentasion.viewmodel.utils.MainDispatcherRule
import com.example.storyapp.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class StoriesRepositoryTest {

    private val dummyMultiPartFile = DataDummy.generateDummyMultipartFile()
    private val dummyDescriptionRequestBody = DataDummy.generateDummyDescriptionRequestBody()
    private val dummyLatitudeRequestBody = DataDummy.generateDummyLatitudeRequestBody()
    private val dummyLongitudeRequestBody = DataDummy.generateDummyLongitudeRequestBody()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    private val loginRequestItem = LoginRequestItem(
        "cwahyu6720@gmail.com",
        "Vouchergamers88"
    )

    private val registerRequestItem = RegisterRequestItem(
        "wahyu",
        "email@gmail.com",
        "password88"
    )

    @Before
    fun setUp() {
        apiService = FakeApiService()
    }

    @Test
    fun `when Login Success NotNull`() = runTest {
        val expectedLoginResponse = DataDummy.generateDummyLoginApiResponse()
        val stories = apiService.postLogin(loginRequestItem)
        Assert.assertNotNull(stories)
        Assert.assertTrue(stories == expectedLoginResponse)
    }

    @Test
    fun `when Register Success NotNull`() = runTest {
        val expectedRegisterResponse = DataDummy.generateDummyRegisterApiResponse()
        val stories = apiService.postRegister(registerRequestItem)
        Assert.assertNotNull(stories)
        Assert.assertTrue(stories == expectedRegisterResponse)
    }

    @Test
    fun `when GetStories Location Success NotNull`() = runTest {
        val expectedGetStoriesLocationResponse = DataDummy.generateDummyGetAllStoriesLocationApiResponse()
        val stories = apiService.getMapStories("token", 1)
        Assert.assertNotNull(stories)
        Assert.assertTrue(stories == expectedGetStoriesLocationResponse)
    }

    @Test
    fun `when GetStories Success NotNull`() = runTest {
        val expectedGetStoriesResponse = DataDummy.generateDummyGetAllStoriesApiResponse()
        val stories = apiService.getAllStories("token", 1)
        Assert.assertNotNull(stories)
        Assert.assertTrue(stories == expectedGetStoriesResponse)
    }

    @Test
    fun `when PostStories Success NotNull`() = runTest {
        val expectedPostStoriesResponse = DataDummy.generateDummyPostStoriesApiResponse()
        val stories = apiService.postStories("token", dummyMultiPartFile, dummyDescriptionRequestBody, dummyLatitudeRequestBody, dummyLongitudeRequestBody)
        Assert.assertNotNull(stories)
        Assert.assertTrue(stories == expectedPostStoriesResponse)
    }


}