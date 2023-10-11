package com.vr.i_mathhear.activity.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import com.vr.i_mathhear.R
import com.vr.i_mathhear.helper.showSnackbar

class LoginActivity : AppCompatActivity() {
    lateinit var buttonLogin:Button
    lateinit var etUsername:EditText
    lateinit var etPassword:EditText
    lateinit var contentView:RelativeLayout
    var username = ""
    var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        initView()
        initClick()
    }
    private fun initView(){
        buttonLogin = findViewById(R.id.buttonLogin)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        contentView = findViewById(R.id.contentView)
    }
    private fun initClick(){
        buttonLogin.setOnClickListener {
            login()
        }
    }
    private fun login(){
        username = etUsername.text.toString()
        password = etPassword.text.toString()
        if(username == "admin" && password == "admin"){
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
            finish()
        }else if (username.isEmpty() && password.isEmpty()){
            etUsername.error = "Username tidak boleh kosong"
            etPassword.error = "Password tidak boleh kosong"
            showSnackbar(contentView, "Username dan Password tidak boleh kosong")
        }else{
            etUsername.error = "Username tidak sesuai"
            etPassword.error = "Password tidak sesuai"
            showSnackbar(contentView, "Username dan Password tidak sesuai")
        }
    }
}