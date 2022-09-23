package com.example.storyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storyapp.data.local.dao.GetAllStoriesDao
import com.example.storyapp.data.local.dao.RemoteKeysDao
import com.example.storyapp.data.local.entity.GetAllStoriesEntity
import com.example.storyapp.data.local.entity.RemoteKeysEntity

@Database(
    entities = [GetAllStoriesEntity::class, RemoteKeysEntity::class],
    version = 2,
    exportSchema = false
)
abstract class StoriesDatabase : RoomDatabase() {

    abstract fun getAllStoriesDao(): GetAllStoriesDao

    abstract fun remoteKeysDao(): RemoteKeysDao

}