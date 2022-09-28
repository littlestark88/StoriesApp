package com.example.storyapp.data


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.storyapp.data.local.StoriesDatabase
import com.example.storyapp.data.local.entity.GetAllStoriesEntity
import com.example.storyapp.data.local.entity.RemoteKeysEntity
import com.example.storyapp.data.remote.network.ApiService
import com.example.storyapp.utils.DataMapper
import com.example.storyapp.utils.SharePreferences
import com.example.storyapp.utils.UserPreferenceKey.INITIAL_PAGE_INDEX
import com.example.storyapp.utils.UserPreferenceKey.LIST_IMAGE

@OptIn(ExperimentalPagingApi::class)
class StoriesRemoteMediator(
    private val database: StoriesDatabase,
    private val apiService: ApiService,
    private val sharePreferences: SharePreferences
) : RemoteMediator<Int, GetAllStoriesEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GetAllStoriesEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData = apiService.getAllStories(sharePreferences.getToken().toString(), state.config.pageSize, page)
            val endOfPaginationReached = responseData.getAllStoriesItem?.isEmpty()
            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.getAllStoriesDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val keys = responseData.getAllStoriesItem?.map {
                    RemoteKeysEntity(id = it.id.toString(), prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                val listImage: ArrayList<String> = ArrayList()
                    for(i in responseData.getAllStoriesItem!!) {
                        listImage.addAll(listOf(i.photoUrl))
                    }
                sharePreferences.saveListString(LIST_IMAGE, listImage)
                database.getAllStoriesDao().insertAllStories(DataMapper.mapGetStoriesEntity(responseData.getAllStoriesItem ))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GetAllStoriesEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GetAllStoriesEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GetAllStoriesEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }
}