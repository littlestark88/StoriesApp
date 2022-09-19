package com.example.storyapp.presentasion.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.register.RegisterRequestItem
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.presentasion.login.LoginActivity
import com.example.storyapp.presentasion.viewmodel.StoriesViewModel
import com.example.storyapp.utils.LoadingUtils.hideLoading
import com.example.storyapp.utils.LoadingUtils.showLoading
import com.example.storyapp.utils.showCustomAlertDialogOneButton
import com.example.storyapp.utils.showCustomAlertDialogTwoButton
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private val storiesViewModel: StoriesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            edtName.addTextChangedListener(registerTextWatcher)
            edtEmail.addTextChangedListener(registerTextWatcher)
            edtPassword.addTextChangedListener(registerTextWatcher)
        }
        actionClick()
    }

    private fun actionClick() {
        with(binding) {
            tvLogin.setOnClickListener {
                intentToLogin()
            }
            btnRegister.setOnClickListener {
                showCustomAlertDialogTwoButton(
                    this@RegisterActivity,
                    message = getString(R.string.label_confirm_register),
                    positiveListener = {
                        hitPostRegister()
                    }
                )
            }
        }

    }

    private fun hitPostRegister() {
        val requestRegister = RegisterRequestItem(
            name, email, password
        )
        storiesViewModel.postRegister(requestRegister)
        postRegisterObserver()
    }

    private fun postRegisterObserver() {
        storiesViewModel.postRegister.observe(this) {
            when (it) {
                is Resource.Loading -> showLoading(this@RegisterActivity)
                is Resource.Success -> {
                    hideLoading()
                    intentToLogin()
                }
                is Resource.Error -> {
                    hideLoading()
                    showCustomAlertDialogOneButton(
                        this@RegisterActivity,
                        message = getString(R.string.label_failed_register)
                    )
                }
            }
        }
    }

    private val registerTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            with(binding) {
                name = edtName.text.toString().trim()
                email = edtEmail.text.toString().trim()
                password = edtPassword.text.toString().trim()
                if (validateAllField(name, email, password)) {
                    btnRegister.isEnabled =
                        name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
                }

            }

        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun validateAllField(name: String, email: String, password: String): Boolean {
        if (name.isEmpty()) {
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        if (password.length < 6) {
            return false
        }
        return true
    }


    private fun intentToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}