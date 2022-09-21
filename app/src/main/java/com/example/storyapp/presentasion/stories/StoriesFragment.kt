package com.example.storyapp.presentasion.stories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.databinding.FragmentStoriesBinding
import com.example.storyapp.domain.data.response.ListGetAllStories
import com.example.storyapp.presentasion.viewmodel.StoriesViewModel
import com.example.storyapp.utils.LoadingUtils.hideLoading
import com.example.storyapp.utils.LoadingUtils.showLoading
import com.example.storyapp.utils.SharePreferences
import com.example.storyapp.utils.UserPreferenceKey.DATA_STORIES
import com.example.storyapp.utils.showCustomAlertDialogOneButton
import org.koin.android.ext.android.inject

class StoriesFragment : Fragment() {

    private var _binding: FragmentStoriesBinding? = null
    private val storiesViewModel: StoriesViewModel by inject()
    private val sharePreferences: SharePreferences by inject()
    private val storiesAdapter: StoriesAdapter by lazy {
        StoriesAdapter(
            mutableListOf(),
            onClickListener = { data, optionCompat ->
                intentDetailStories(data, optionCompat)
            },
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
        binding?.tes?.setOnClickListener {
            storiesViewModel.getStories(sharePreferences.getToken().toString())
            storiesViewModel.getStories.observe(viewLifecycleOwner) {

            }
        }

//        storiesViewModel.getStories.observe(viewLifecycleOwner) {
//            if(it.data?.listGetStories?.isNotEmpty() == true) {
//                when(it) {
//                    is Resource.Loading -> showLoading(requireContext())
//                    is Resource.Empty -> {
//                        hideLoading()
//                        binding?.rvStories?.visibility = View.GONE
//                        binding?.emptyAnimation?.visibility = View.VISIBLE
//                    }
//                    is Resource.Success -> {
//                        hideLoading()
//                        binding?.rvStories?.visibility = View.VISIBLE
//                        binding?.emptyAnimation?.visibility = View.GONE
//                        it.data.listGetStories.let { dataList ->
//                            storiesAdapter.setListStories(dataList)
//                            showRecyclerListStories()
//                        }
//                    }
//                    is Resource.Error -> {
//                        hideLoading()
//                        showCustomAlertDialogOneButton(
//                            requireActivity(),
//                            message = getString(R.string.label_failed_load_data)
//                        )
//                    }
//                }
//            }
//
//        }
    }

    private fun showRecyclerListStories () {
        with(binding) {
            this?.rvStories?.layoutManager = LinearLayoutManager(context)
            this?.rvStories?.setHasFixedSize(true)
            this?.rvStories?.adapter = storiesAdapter
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