package com.example.storyapp.presentasion.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.data.lib.Resource
import com.example.storyapp.data.remote.request.login.LoginRequestItem
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.presentasion.MainActivity
import com.example.storyapp.presentasion.register.RegisterActivity
import com.example.storyapp.presentasion.viewmodel.StoriesViewModel
import com.example.storyapp.utils.LoadingUtils.hideLoading
import com.example.storyapp.utils.LoadingUtils.showLoading
import com.example.storyapp.utils.showCustomAlertDialogOneButton
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var email: String = ""
    private var password: String = ""
    private val storiesViewModel: StoriesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            edtEmail.addTextChangedListener(loginTextWatcher)
            edtPassword.addTextChangedListener(loginTextWatcher)

            btnLogin.setOnClickListener {
                hitPostLogin()
            }
            tvRegister.setOnClickListener {
                intentToRegister()
            }
        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            with(binding) {
                email = edtEmail.text.toString().trim()
                password = edtPassword.text.toString().trim()
                if(validateAllField(email, password)) {
                    btnLogin.isEnabled = email.isNotEmpty() && password.isNotEmpty()
                }
            }

        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun hitPostLogin() {
        val loginRequest = LoginRequestItem(
            email,
            password
        )
        storiesViewModel.postLogin(loginRequest)
        postLoginObserver()
    }

    private fun postLoginObserver() {
        storiesViewModel.postLogin.observe(this) {
            when(it) {
                is Resource.Loading -> showLoading(this@LoginActivity)
                is Resource.Success -> {
                    hideLoading()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Resource.Error -> {
                    hideLoading()
                    showCustomAlertDialogOneButton(
                        this@LoginActivity,
                        message = getString(R.string.label_failed_login)
                    )
                }
            }
        }
    }

    private fun validateAllField(email: String, password: String): Boolean {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        if(password.length < 6) {
            return false
        }
        return true
    }

    private fun intentToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}