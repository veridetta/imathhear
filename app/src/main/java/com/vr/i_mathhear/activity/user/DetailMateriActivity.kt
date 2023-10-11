package com.vr.i_mathhear.activity.user

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import com.vr.i_mathhear.MainActivity
import com.vr.i_mathhear.R
import com.vr.i_mathhear.helper.extractVideoIdFromUrl

class DetailMateriActivity : AppCompatActivity() {
    lateinit var btnHome : ImageView
    lateinit var btnBack : LinearLayout
    lateinit var webView: WebView
    var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_detail_materi)
        initView()
        initClick()
        initIntent()
        initWebview(extractVideoIdFromUrl(url)!!)
    }
    private fun initView(){
        btnHome = findViewById(R.id.btnHome)
        btnBack = findViewById(R.id.btnBack)
        webView = findViewById(R.id.webView)
    }
    private fun initClick(){
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnBack.setOnClickListener {
            finish()
        }
    }
    private fun initIntent(){
        url = intent.getStringExtra("url").toString()
    }
    private fun initWebview(ytUrl: String) {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        val videoUrl = "https://www.youtube.com/embed/$ytUrl"
        val iframe = "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>"
        // Muat URL video YouTube ke dalam WebView
        webView.loadData(iframe, "text/html", "utf-8")
        webView.webChromeClient = WebChromeClient()
    }

}