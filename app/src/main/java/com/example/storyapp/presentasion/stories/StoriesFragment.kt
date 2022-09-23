package com.example.storyapp.presentasion.stories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.databinding.FragmentStoriesBinding
import com.example.storyapp.domain.data.response.ListGetAllStories
import com.example.storyapp.presentasion.viewmodel.StoriesViewModel
import com.example.storyapp.utils.SharePreferences
import com.example.storyapp.utils.UserPreferenceKey.DATA_STORIES
import org.koin.android.ext.android.inject

class StoriesFragment : Fragment() {

    private var _binding: FragmentStoriesBinding? = null
    private val storiesViewModel: StoriesViewModel by inject()
    private val sharePreferences: SharePreferences by inject()
    private val storiesAdapter: StoriesAdapter by lazy {
        StoriesAdapter(
            onClickListener = { data, optionCompat ->
                intentDetailStories(data, optionCompat)
            }
        )
    }

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoriesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerListStories()
        storiesViewModel.getStories(sharePreferences.getToken().toString())
            .observe(viewLifecycleOwner) {
                storiesAdapter.submitData(lifecycle, it)
            }
    }

    private fun showRecyclerListStories() {
        binding?.rvStories.apply {
            this?.layoutManager = LinearLayoutManager(requireContext())
            this?.setHasFixedSize(true)
            this?.adapter = storiesAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storiesAdapter.retry()
                }
            )
        }
    }

    private fun intentDetailStories(data: ListGetAllStories, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(requireActivity(), DetailStoriesActivity::class.java)
        intent.putExtra(DATA_STORIES, data)
        startActivity(intent, optionsCompat.toBundle())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}