package com.example.storyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GetAllStoriesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoriesDatabase : RoomDatabase() {

    abstract fun getAllStoriesDao(): GetAllStoriesDao

}