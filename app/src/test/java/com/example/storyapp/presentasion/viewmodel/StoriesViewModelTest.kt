package com.example.storyapp.presentasion.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.domain.IStoriesUseCase
import com.example.storyapp.domain.data.response.ListGetAllStories
import com.example.storyapp.domain.data.response.Login
import com.example.storyapp.domain.data.response.Register
import com.example.storyapp.presentasion.stories.StoriesAdapter
import com.example.storyapp.presentasion.viewmodel.utils.MainDispatcherRule
import com.example.storyapp.presentasion.viewmodel.utils.getOrAwaitValue
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.PagedTestDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
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
class StoriesViewModelTest {

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storiesUseCase: IStoriesUseCase
    private lateinit var storiesViewModel: StoriesViewModel
    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummyLoginFailResponse = DataDummy.generateDummyLoginFailResponse()
    private val dummyLoginRegisterResponse = DataDummy.generateDummyRegister()
    private val dummyPostStoriesResponse = DataDummy.generateDummyPostStories()
    private val dummyMultiPartFile = DataDummy.generateDummyMultipartFile()
    private val dummyDescriptionRequestBody = DataDummy.generateDummyDescriptionRequestBody()
    private val dummyLatitudeRequestBody = DataDummy.generateDummyLatitudeRequestBody()
    private val dummyLongitudeRequestBody = DataDummy.generateDummyLongitudeRequestBody()
    private val dummyGetAllStoriesResponse = DataDummy.generateDummyGetAllStories()
    private val dummyGetAllStoriesLocationResponse = DataDummy.generateDummyGetAllStoriesLocationResponse()
    private val dummyToken = "authentication_token"
    private val loginRequestItem = LoginRequestItem(
        "cwahyu6720@gmail.com",
        "Vouchergamers88"
    )

    private val loginRequestItemFail = LoginRequestItem(
        "email@gmail.com",
        "password88"
    )

    private val registerRequestItem = RegisterRequestItem(
        "yer",
        "qwtqetwerwr@gmail.com",
        "ttttttttt"
    )

    @Before
    fun setUp() {
        storiesViewModel = StoriesViewModel(storiesUseCase)
    }

    @Test
    fun `when Login listLogin Success NotNull`(): Unit = runTest {
        val expectedResponse = flow {
            emit(Resource.Success(dummyLoginResponse))
        }
        `when`(storiesViewModel.postLogin(loginRequestItem)).thenReturn(expectedResponse)
        val stories = storiesViewModel.postLogin(loginRequestItem)
        Assert.assertNotNull(stories)

        Mockito.verify(storiesUseCase).postLogin(loginRequestItem)
    }

    @Test
    fun `when Login listLogin Error NotNull`(): Unit = runTest {
        val expectedResponse: Flow<Resource<Login>> = flow {
            emit(Resource.Error("Invalid password", dummyLoginFailResponse))
        }
        `when`(storiesViewModel.postLogin(loginRequestItemFail)).thenReturn(expectedResponse)
        val stories = storiesViewModel.postLogin(loginRequestItemFail)
        Assert.assertNotNull(stories)

        Mockito.verify(storiesUseCase).postLogin(loginRequestItemFail)
    }

    @Test
    fun `when Register response Success NotNull`(): Unit = runTest {
        val expectedResponse = flow {
            emit(Resource.Success(dummyLoginRegisterResponse))
        }
        `when`(storiesViewModel.postRegister(registerRequestItem)).thenReturn(expectedResponse)
        val registerStories = storiesViewModel.postRegister(registerRequestItem)
        Assert.assertNotNull(registerStories)

        Mockito.verify(storiesUseCase).postRegister(registerRequestItem)
    }

    @Test
    fun `when Register response Error NotNull`(): Unit = runTest {
        val expectedResponse: Flow<Resource<Register>> = flow {
            emit(Resource.Error("Invalid register", dummyLoginRegisterResponse))
        }
        `when`(storiesViewModel.postRegister(registerRequestItem)).thenReturn(expectedResponse)
        val registerStories = storiesViewModel.postRegister(registerRequestItem)
        Assert.assertNotNull(registerStories)

        Mockito.verify(storiesUseCase).postRegister(registerRequestItem)
    }

    @Test
    fun `when Post Stories response Success NotNull`(): Unit = runTest {
        val expectedResponse = flow {
            emit(Resource.Success(dummyPostStoriesResponse))
        }
        `when`(
            storiesViewModel.postStories(
                dummyToken,
                dummyMultiPartFile,
                dummyDescriptionRequestBody,
                dummyLatitudeRequestBody,
                dummyLongitudeRequestBody
            )
        ).thenReturn(expectedResponse)
        val registerStories = storiesViewModel.postStories(
            dummyToken,
            dummyMultiPartFile,
            dummyDescriptionRequestBody,
            dummyLatitudeRequestBody,
            dummyLongitudeRequestBody
        )
        Assert.assertNotNull(registerStories)

        Mockito.verify(storiesUseCase).postStories(
            dummyToken,
            dummyMultiPartFile,
            dummyDescriptionRequestBody,
            dummyLatitudeRequestBody,
            dummyLongitudeRequestBody
        )
    }

    @Test
    fun `when Post Stories response error NotNull`(): Unit = runTest {
        val expectedResponse = flow {
            emit(Resource.Error("Invalid Create stories", dummyPostStoriesResponse))
        }
        `when`(
            storiesViewModel.postStories(
                dummyToken,
                dummyMultiPartFile,
                dummyDescriptionRequestBody,
                dummyLatitudeRequestBody,
                dummyLongitudeRequestBody
            )
        ).thenReturn(expectedResponse)
        val registerStories = storiesViewModel.postStories(
            dummyToken,
            dummyMultiPartFile,
            dummyDescriptionRequestBody,
            dummyLatitudeRequestBody,
            dummyLongitudeRequestBody
        )
        Assert.assertNotNull(registerStories)

        Mockito.verify(storiesUseCase).postStories(
            dummyToken,
            dummyMultiPartFile,
            dummyDescriptionRequestBody,
            dummyLatitudeRequestBody,
            dummyLongitudeRequestBody
        )
    }

    @Test
    fun `when Get All Stories response Success NotNull`() = runTest {
        val data : PagingData<ListGetAllStories> = PagedTestDataSource.snapshot(dummyGetAllStoriesResponse)
        val expectedStoriesResponse = MutableLiveData<PagingData<ListGetAllStories>>()
        expectedStoriesResponse.value = data
        `when`(storiesUseCase.getAllStories(dummyToken)).thenReturn(
            expectedStoriesResponse.asFlow()
        )

        val getAllStories = storiesViewModel.getStories(dummyToken)
        val actualStories: PagingData<ListGetAllStories> = getAllStories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = mainDispatcherRule.testDispatcher
        )
        differ.submitData(actualStories)
        Assert.assertNotNull(differ.snapshot())
    }

    @Test
    fun `when Get All Stories Location response Success NotNull`(): Unit = runTest {
        val expectedResponse = flow {
            emit(Resource.Success(dummyGetAllStoriesLocationResponse))
        }
        `when`(storiesViewModel.getMapStories(dummyToken)).thenReturn(expectedResponse)
        val getMapStories = storiesViewModel.getMapStories(dummyToken)
        Assert.assertNotNull(getMapStories)

        Mockito.verify(storiesUseCase).getMapStories(dummyToken)
    }

    @Test
    fun `when Get All Stories Location response Error NotNull`(): Unit = runTest {
        val expectedResponse = flow {
            emit(Resource.Error("Invalid register", dummyGetAllStoriesLocationResponse))
        }
        `when`(storiesViewModel.getMapStories(dummyToken)).thenReturn(expectedResponse)
        val getMapStories = storiesViewModel.getMapStories(dummyToken)
        Assert.assertNotNull(getMapStories)

        Mockito.verify(storiesUseCase).getMapStories(dummyToken)
    }


}

private val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}