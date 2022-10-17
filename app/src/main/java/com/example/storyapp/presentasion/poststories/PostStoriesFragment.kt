package com.example.storyapp.presentasion.poststories

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.storyapp.R
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.databinding.FragmentPostStoriesBinding
import com.example.storyapp.presentasion.camera.CameraActivity
import com.example.storyapp.presentasion.viewmodel.StoriesViewModel
import com.example.storyapp.utils.*
import com.example.storyapp.utils.LoadingUtils.hideLoading
import com.example.storyapp.utils.LoadingUtils.showLoading
import com.example.storyapp.utils.UserPreferenceKey.CAMERA_X_RESULT
import com.example.storyapp.utils.UserPreferenceKey.IS_BACK_CAMERA
import com.example.storyapp.utils.UserPreferenceKey.PICTURE
import com.example.storyapp.utils.UserPreferenceKey.REQUEST_CODE_PERMISSIONS
import com.example.storyapp.utils.UserPreferenceKey.REQUIRED_PERMISSIONS
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File

class PostStoriesFragment : Fragment() {

    private var _binding: FragmentPostStoriesBinding? = null
    private var getFile: File? = null
    private val storiesViewModel: StoriesViewModel by inject()
    private val sharePreferences: SharePreferences by inject()
    private var description: String = ""
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostStoriesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        with(binding) {
            this?.btnCamera?.setOnClickListener { intentCameraX() }
            this?.btnGallery?.setOnClickListener { intentGallery() }
            this?.btnUpload?.setOnClickListener {
                showCustomAlertDialogTwoButton(
                    requireActivity(),
                    message = getString(R.string.label_confirm_upload),
                    positiveListener = {

                        val latitude = sharePreferences.getLatitude().toString()
                        val longitude = sharePreferences.getLongitude().toString()
                        if(latitude.isNotEmpty() && longitude.isNotEmpty()) {
                            hitPostStories(latitude, longitude)
                        } else {
                            showCustomAlertDialogOneButton(
                                requireContext(),
                                message = getString(R.string.label_permission_on_maps)
                            )
                        }
                    }
                )
            }
            this?.edtDescription?.addTextChangedListener(descriptionTextWatcher)
        }
    }

    private val descriptionTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            with(binding) {
                description = this?.edtDescription?.text.toString().trim()
                this?.btnUpload?.isEnabled = description.isNotEmpty() && getFile != null
            }

        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun hitPostStories(latitude: String, longitude: String) {
        val file = reduceFileImage(getFile as File)
        val requestDescription = description.toRequestBody("text/plain".toMediaType())
        val requestLatitude = latitude.toRequestBody("text/plain".toMediaType())
        val requestLongitude = longitude.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            getString(R.string.label_photo),
            file.name,
            requestImageFile
        )
        storiesViewModel.postStories(
            sharePreferences.getToken().toString(),
            imageMultipart,
            requestDescription,
            requestLatitude,
            requestLongitude
        )
        postStoriesObserver()

    }

    private fun postStoriesObserver() {
        storiesViewModel.postStories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> showLoading(requireActivity())
                is Resource.Success -> {
                    hideLoading()
                    showCustomAlertDialogOneButton(
                        requireContext(),
                        message = getString(R.string.label_success_post_stories),
                        positiveListener = {
                            view?.findNavController()
                                ?.navigate(R.id.action_navigation_post_stories_to_navigation_stories)
                        }
                    )
                }
                is Resource.Error -> {
                    hideLoading()
                    showCustomAlertDialogOneButton(
                        requireActivity(),
                        message = getString(R.string.label_failed_post_stories)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun intentGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.label_choose_picture))
        launcherIntentGallery.launch(chooser)
    }

    private fun intentCameraX() {
        val intent = Intent(context, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.label_denied_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra(PICTURE) as File
            val isBackCamera = it.data?.getBooleanExtra(IS_BACK_CAMERA, true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            binding?.imgPhoto?.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, requireActivity())

            getFile = myFile

            binding?.imgPhoto?.setImageURI(selectedImg)
        }
    }


}