package com.vr.i_mathhear.activity.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.vr.i_mathhear.MainActivity
import com.vr.i_mathhear.R
import com.vr.i_mathhear.helper.animateClick

class GamesActivity : AppCompatActivity() {
    lateinit var btnAngka : CardView
    lateinit var btnHitung : CardView
    lateinit var btnBack : LinearLayout
    lateinit var btnHome : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_games)
        initView()
        initClick()
        initAnimation()

    }
    private fun initView(){
        btnAngka = findViewById(R.id.btnAngka)
        btnHitung = findViewById(R.id.btnHitung)
        btnBack = findViewById(R.id.btnBack)
        btnHome = findViewById(R.id.btnHome)
    }
    private fun initClick(){
        btnAngka.setOnClickListener {
            animateClick(btnAngka)
            val intent = Intent(this, AngkaActivity::class.java)
            startActivity(intent)
        }
        btnHitung.setOnClickListener {
            animateClick(btnHitung)
            val intent = Intent(this, HitungActivity::class.java)
            startActivity(intent)
        }
        btnBack.setOnClickListener {
            animateClick(btnBack)
            finish()
        }
        btnHome.setOnClickListener {
            animateClick(btnHome)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun initAnimation(){
        btnAngka.animate().translationY(0f).duration = 1000
        btnHitung.animate().translationY(0f).duration = 1000
        btnBack.animate().translationY(0f).duration = 1000
        btnHome.animate().translationY(0f).duration = 1000
    }

}