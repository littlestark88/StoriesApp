package com.example.storyapp.utils

import android.Manifest

object UserPreferenceKey {
    const val USER = "user"
    const val TOKEN = "token"
    const val PICTURE = "picture"
    const val IS_BACK_CAMERA = "isBackCamera"
    const val CAMERA_X_RESULT = 200
    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA
    )
    const val REQUEST_CODE_PERMISSIONS = 10
    const val DATA_STORIES = "data_stories"
    const val LIST_IMAGE = "list_image"
    const val TOAST_ACTION = "com.example.storyapp.TOAST_ACTION"
    const val EXTRA_ITEM = "com.example.storyapp.EXTRA_ITEM"
    const val MAP_LATITUDE = "map_latitude"
    const val MAP_LONGITUDE = "map_longitude"
    const val INITIAL_PAGE_INDEX = 1
}