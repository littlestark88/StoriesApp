package com.example.storyapp.presentasion.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentSettingBinding
import com.example.storyapp.presentasion.login.LoginActivity
import com.example.storyapp.utils.SharePreferences
import com.example.storyapp.utils.showCustomAlertDialogTwoButton
import org.koin.android.ext.android.inject

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val sharePreferences: SharePreferences by inject()

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            this?.btnChangeLanguage?.setOnClickListener { intentLanguageSetting() }
            this?.btnLogout?.setOnClickListener {
                showCustomAlertDialogTwoButton(
                    requireActivity(),
                    message = getString(R.string.label_confirm_logout),
                    positiveListener = {
                        intentLogOut()
                    }
                )
            }
        }
    }

    private fun intentLanguageSetting() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }

    private fun intentLogOut() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
        sharePreferences.clearPreferences()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}