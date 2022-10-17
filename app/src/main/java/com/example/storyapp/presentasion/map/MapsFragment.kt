package com.example.storyapp.presentasion.map

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.storyapp.R
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.databinding.FragmentMapsBinding
import com.example.storyapp.presentasion.viewmodel.StoriesViewModel
import com.example.storyapp.utils.LoadingUtils.hideLoading
import com.example.storyapp.utils.LoadingUtils.showLoading
import com.example.storyapp.utils.SharePreferences
import com.example.storyapp.utils.showCustomAlertDialogOneButton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
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
    private val sharePreferences: SharePreferences by inject()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        getDeviceLocation()
        setMapStyle()
        storiesViewModel.getMapStories(sharePreferences.getToken().toString())
        getMarkStoriesLocation()
    }

    private fun getMarkStoriesLocation() {
        storiesViewModel.getMapStories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> showLoading(requireContext())
                is Resource.Success -> {
                    hideLoading()
                    it.data?.listGetStoriesLocation?.forEach { listStories ->
                        val latLong =LatLng(listStories.latitude, listStories.longitude)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(latLong)
                                .title(listStories.name)
                                .snippet(listStories.description)
                        )
                    }
                }
                is Resource.Error -> {
                    hideLoading()
                    showCustomAlertDialogOneButton(
                        requireContext(),
                        message = getString(R.string.label_failed_get_map)
                    )
                }
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


    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getDeviceLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latLong = LatLng(location.latitude, location.longitude)
                    sharePreferences.saveLatitude(location.latitude.toString())
                    sharePreferences.saveLongitude(location.longitude.toString())
                   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 8f))
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.label_give_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permission ->
            when {
                permission[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getDeviceLocation()
                }
                permission[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getDeviceLocation()
                }
                else -> {

                }
            }
        }


}