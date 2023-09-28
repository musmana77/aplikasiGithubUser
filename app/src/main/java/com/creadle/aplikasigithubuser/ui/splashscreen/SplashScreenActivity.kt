package com.creadle.aplikasigithubuser.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.creadle.aplikasigithubuser.R
import com.creadle.aplikasigithubuser.databinding.ActivitySplashScreenBinding
import com.creadle.aplikasigithubuser.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

            val background : ImageView = binding.logo
            val animation = AnimationUtils.loadAnimation(this@SplashScreenActivity,R.anim.rotate)
            background.startAnimation(animation)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }

}