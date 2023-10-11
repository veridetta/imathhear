package com.vr.i_mathhear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {
    lateinit var splash_image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_splash)
        initView()
        animateView()
        goToMainActivity()
    }
    private fun initView(){
        splash_image = findViewById(R.id.splash_image)
    }
    private fun animateView(){
        splash_image.animate().alpha(1f).duration = 1000
    }
    private fun goToMainActivity(){
        //handler 1,2detik
        val handler = android.os.Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1200)
    }
}