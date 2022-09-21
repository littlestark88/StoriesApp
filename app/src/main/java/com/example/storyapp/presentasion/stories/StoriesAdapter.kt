package com.example.storyapp.presentasion.stories

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.databinding.ItemListStoriesBinding
import com.example.storyapp.domain.data.response.ListGetAllStories

class StoriesAdapter
//    (
//    private val data: MutableList<ListGetAllStories>,
//    private val onClickListener: (ListGetAllStories, ActivityOptionsCompat) -> Unit,
//)
    : PagingDataAdapter<ListGetAllStories, StoriesAdapter.StoriesViewHolder>(DIFF_CALLBACK) {

//    fun setListStories(addItemListStories: List<ListGetAllStories>?) {
//        data.clear()
//        addItemListStories?.let { data.addAll(it) }
//        notifyItemChanged(itemCount)
//    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListGetAllStories>() {
            override fun areItemsTheSame(
                oldItem: ListGetAllStories,
                newItem: ListGetAllStories
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListGetAllStories,
                newItem: ListGetAllStories
            ): Boolean {
                return oldItem ==newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val binding =
            ItemListStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
//        holder.bind(data[position])
    }

//    override fun getItemCount(): Int = data.size

    inner class StoriesViewHolder(private val binding: ItemListStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListGetAllStories) {
            with(binding) {
                tvName.text = data.name
                tvDescription.text = data.description
                Glide
                    .with(itemView)
                    .load(data.photoUrl)
                    .placeholder(R.drawable.ic_refresh)
                    .into(imgPoster)
                container.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgPoster, "photo"),
                            Pair(tvName, "name"),
                            Pair(tvDescription, "description"),
                        )

//                    onClickListener(data, optionsCompat)
                }
            }
        }

    }

}