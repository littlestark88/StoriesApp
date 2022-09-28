package com.example.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.local.StoriesDatabase
import com.example.storyapp.data.remote.RemoteDataSource
import com.example.storyapp.data.remote.network.ApiService
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.presentasion.viewmodel.utils.MainDispatcherRule
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.SharePreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class StoriesRepositoryTest {

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var databaseStories: StoriesDatabase

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var remoteDataSource: RemoteDataSource
    @Mock
    private lateinit var sharePreferences: SharePreferences
    @Mock
    private lateinit var storiesRepository: StoriesRepository
    private val dummyLoginResponse = DataDummy.generateDummyLoginResponseRepository()

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
        storiesRepository = StoriesRepository(databaseStories, apiService, sharePreferences, remoteDataSource)
    }

    @Test
    fun `when Login listLogin Success NotNull`() = runTest {
        val expectedResponse = flow {
            emit(Resource.Success(dummyLoginResponse))
        }
//        `when`(storiesRepository.postLogin(loginRequestItem)).thenReturn(expectedResponse)
        val stories = storiesRepository.postLogin(loginRequestItem)
        Assert.assertTrue(stories == expectedResponse)
    }

    @Test
    fun `when Register Success NotNull`() = runTest {
        val stories = storiesRepository.postRegister(registerRequestItem)
        Assert.assertNotNull(stories)
    }

}