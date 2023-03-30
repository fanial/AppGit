package com.codefal.appgit.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.codefal.appgit.MainActivity
import com.codefal.appgit.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private var _binding : ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val splashTime : Long = 3000

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
            }, splashTime)
    }

    override fun onResume() {
        super.onResume()
        this.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        this.supportActionBar?.show()
    }
}