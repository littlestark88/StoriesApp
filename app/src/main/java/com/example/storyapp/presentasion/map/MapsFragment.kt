package com.example.storyapp.presentasion.map

import android.content.ContentValues.TAG
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentMapsBinding
import com.example.storyapp.presentasion.viewmodel.StoriesViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private var _binding: FragmentMapsBinding? = null
    private val storiesViewModel: StoriesViewModel by inject()

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

//        getDeviceLocation()
        setMapStyle()
        getMarkStoriesLocation()
    }

    private fun getMarkStoriesLocation() {
        storiesViewModel.getStoriesLocal.observe(viewLifecycleOwner) {
            it.forEach { stories ->
                val latLong =LatLng(stories.latitude, stories.longitude)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLong)
                        .title(stories.name)
                        .snippet("Lat: ${stories.latitude}, Lon: ${stories.longitude}")
                )
            }
        }
    }


    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!success) {
                Log.e(TAG, getString(R.string.label_fail_parsing_style))
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }




}