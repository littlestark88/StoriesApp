package com.example.storyapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storyapp.data.local.entity.GetAllStoriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GetAllStoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStories(allStoriesEntity: List<GetAllStoriesEntity>)

    @Query("SELECT * FROM GetAllStoriesTable")
    fun getAllStories(): PagingSource<Int, GetAllStoriesEntity>

    @Query("SELECT * FROM GetAllStoriesTable")
    fun getAllStoriesWithOutPaging(): Flow<List<GetAllStoriesEntity>>

    @Query("DELETE FROM GetAllStoriesTable")
    suspend fun deleteAll()
}