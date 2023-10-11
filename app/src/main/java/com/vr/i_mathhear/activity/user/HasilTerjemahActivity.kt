package com.vr.i_mathhear.activity.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.vr.i_mathhear.MainActivity
import com.vr.i_mathhear.R

class HasilTerjemahActivity : AppCompatActivity() {
    lateinit var tvTerjemah: TextView
    lateinit var btnBack: LinearLayout
    lateinit var btnHome: ImageView
    var hasil = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_hasil_terjemah)
        initView()
        initClick()
        initIntent()
    }
    private fun initView(){
        tvTerjemah = findViewById(R.id.tvTerjemah)
        btnBack = findViewById(R.id.btnBack)
        btnHome = findViewById(R.id.btnHome)
    }
    private fun initClick(){
        btnBack.setOnClickListener {
            finish()
        }
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun initIntent(){
        hasil = intent.getStringExtra("hasil").toString()
        tvTerjemah.text = hasil
    }
}