package com.vr.i_mathhear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import com.vr.i_mathhear.activity.admin.AdminActivity
import com.vr.i_mathhear.activity.admin.LoginActivity
import com.vr.i_mathhear.activity.user.GamesActivity
import com.vr.i_mathhear.activity.user.InfoActivity
import com.vr.i_mathhear.activity.user.MateriActivity
import com.vr.i_mathhear.activity.user.TerjemahActivity

class MainActivity : AppCompatActivity() {
    lateinit var lyButton : RelativeLayout
    lateinit var imgSiswa : ImageView
    lateinit var btnAdmin : ImageView
    lateinit var imgLogo : LinearLayout
    lateinit var btnMateri : CardView
    lateinit var btnGames : CardView
    lateinit var btnTerjemahkan : CardView
    lateinit var btnInfo : CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_main)
        initView()
        animateView()
        initClick()
    }

    private fun initView(){
        lyButton = findViewById(R.id.lyButton)
        imgSiswa = findViewById(R.id.imgSiswa)
        imgLogo = findViewById(R.id.imgLogo)
        btnMateri = findViewById(R.id.btnMateri)
        btnGames = findViewById(R.id.btnGames)
        btnTerjemahkan = findViewById(R.id.btnTerjemahkan)
        btnInfo = findViewById(R.id.btnInfo)
        btnAdmin = findViewById(R.id.btnAdmin)
    }
    private fun animateView(){
        lyButton.animate().translationY(0f).duration = 1000
        imgSiswa.animate().translationY(0f).duration = 1000
        imgLogo.animate().translationY(0f).duration = 1000
        btnMateri.animate().translationY(0f).duration = 1000
        btnGames.animate().translationY(0f).duration = 1000
        btnTerjemahkan.animate().translationY(0f).duration = 1000
        btnInfo.animate().translationY(0f).duration = 1000

    }
    private fun initClick(){
        btnMateri.setOnClickListener {
            animateClick(btnMateri)
            val intent = Intent(this, MateriActivity::class.java)
            startActivity(intent)
        }
        btnGames.setOnClickListener {
            animateClick(btnGames)
            val intent = Intent(this, GamesActivity::class.java)
            startActivity(intent)
        }
        btnTerjemahkan.setOnClickListener {
            animateClick(btnTerjemahkan)
            val intent = Intent(this, TerjemahActivity::class.java)
            startActivity(intent)
        }
        btnInfo.setOnClickListener {
            animateClick(btnInfo)
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
        btnAdmin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun animateClick(cardView: CardView){
        cardView.animate().scaleX(0.9f).scaleY(0.9f).duration = 100
        cardView.animate().scaleX(1f).scaleY(1f).duration = 100
    }
}