package com.vr.i_mathhear.activity.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.vr.i_mathhear.R

class InfoActivity : AppCompatActivity() {
    lateinit var btnBack : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_info)
        initView()
        initClick()
    }

    private fun initView(){
        btnBack = findViewById(R.id.btnBack)
    }
    private fun initClick(){
        btnBack.setOnClickListener {
            finish()
        }
    }
}