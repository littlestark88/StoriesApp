package com.example.storyapp.data


import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.storyapp.data.local.GetAllStoriesEntity
import com.example.storyapp.data.local.StoriesDatabase
import com.example.storyapp.data.remote.network.ApiService
import com.example.storyapp.utils.DataMapper
import com.example.storyapp.utils.UserPreferenceKey.INITIAL_PAGE_INDEX

@OptIn(ExperimentalPagingApi::class)
class StoriesRemoteMediator(
    private val database: StoriesDatabase,
    private val apiService: ApiService,
    private val token: String
) : RemoteMediator<Int, GetAllStoriesEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GetAllStoriesEntity>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        val location = 1

        return try {
            val responseData = apiService.getAllStories(token, state.config.pageSize, page, location)
            val endOfPaginationReached = responseData.isEmpty()
            Log.e("loadStories" ,"remoteMediator: 1" )
            database.withTransaction {
                Log.e("loadStories" ,"remoteMediator: 2" )
                if(loadType == LoadType.REFRESH) {
                    Log.e("loadStories" ,"remoteMediator: 3" )
                    database.getAllStoriesDao().deleteAll()
                }
                database.getAllStoriesDao().insertAllStories(DataMapper.mapGetStoriesEntity(responseData))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}