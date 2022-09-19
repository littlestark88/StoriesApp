package com.example.storyapp.presentasion.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storyapp.databinding.ActivityOnBoardingBinding
import com.example.storyapp.presentasion.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener { intentLogin() }
    }

    private fun intentLogin() {
        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}