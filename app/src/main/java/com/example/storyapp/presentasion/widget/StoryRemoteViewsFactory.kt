package com.example.storyapp.presentasion.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.utils.UserPreferenceKey.EXTRA_ITEM
import com.example.storyapp.utils.UserPreferenceKey.LIST_IMAGE
import com.example.storyapp.utils.getListPreference

class StoryRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val data = ArrayList<String>()
    override fun onCreate() {
        val listImage = getListPreference(context, LIST_IMAGE)
        listImage?.let {
            data.addAll(it)
        }
    }

    override fun onDataSetChanged() {
        val updateImage = getListPreference(context, LIST_IMAGE)
        updateImage?.let {
            data.addAll(it)
        }
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = data.size

    override fun getViewAt(position: Int): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.widget_item)
            val image = Glide.with(context)
                .asBitmap()
                .load(data[position])
                .submit()
                .get()
            remoteView.setImageViewBitmap(R.id.imgWidgetStory, image)

        val extras = bundleOf(
            EXTRA_ITEM to position
        )

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)


        remoteView.setOnClickFillInIntent(R.id.imgWidgetStory, fillInIntent)
        return remoteView
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}
