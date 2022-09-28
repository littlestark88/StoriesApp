package com.example.storyapp.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.domain.data.response.ListGetAllStories

class PagedTestDataSource : PagingSource<Int, LiveData<List<ListGetAllStories>>>() {

    companion object {
        fun snapshot(items: List<ListGetAllStories>): PagingData<ListGetAllStories> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListGetAllStories>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListGetAllStories>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}