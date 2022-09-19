package com.example.storyapp.presentasion.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.databinding.ActivitySplashScreenBinding
import com.example.storyapp.presentasion.MainActivity
import com.example.storyapp.presentasion.onboarding.OnBoardingActivity
import com.example.storyapp.utils.SharePreferences
import org.koin.android.ext.android.inject


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val sharePreferences: SharePreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intentOnBoarding()
    }

    private fun intentOnBoarding() {
        Handler(Looper.getMainLooper()).postDelayed({
            if(sharePreferences.getToken()?.isNotEmpty() == true) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }

}