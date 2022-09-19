package com.example.storyapp.presentasion.stories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ActivityDetailStoriesBinding
import com.example.storyapp.domain.data.response.ListGetAllStories
import com.example.storyapp.utils.UserPreferenceKey.DATA_STORIES

class DetailStoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<ListGetAllStories>(DATA_STORIES)
        with(binding) {
            tvName.text = data?.name
            tvDescription.text = data?.description
            Glide
                .with(this@DetailStoriesActivity)
                .load(data?.photoUrl)
                .into(imgPhoto )
        }
    }
}