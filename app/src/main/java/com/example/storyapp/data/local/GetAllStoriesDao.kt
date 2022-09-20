package com.example.storyapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GetAllStoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStories(allStoriesEntity: List<GetAllStoriesEntity>)

    @Query("SELECT * FROM GetAllStoriesTable")
    fun getAllStories(): PagingSource<Int, GetAllStoriesEntity>

    @Query("DELETE FROM GetAllStoriesTable")
    suspend fun deleteAll()
}