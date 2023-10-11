package com.vr.i_mathhear.activity.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vr.i_mathhear.R
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.vr.i_mathhear.MainActivity
import com.vr.i_mathhear.helper.addRating
import com.vr.i_mathhear.helper.generateRandomAngka
import com.vr.i_mathhear.helper.getGambar
import com.vr.i_mathhear.helper.showSnackbar


class AngkaActivity : AppCompatActivity() {
    lateinit var soalSatu  : ImageView
    lateinit var soalDua   : ImageView
    lateinit var jawabSatu   : TextView
    lateinit var jawabDua   : TextView
    lateinit var panahSatuLeft   : ImageView
    lateinit var panahSatuRight   : ImageView
    lateinit var panahDuaLeft   : ImageView
    lateinit var panahDuaRight   : ImageView
    lateinit var lyInduk   : LinearLayout
    lateinit var contentUtama   : ConstraintLayout
    lateinit var btnBack   : LinearLayout
    lateinit var btnNext   : LinearLayout
    lateinit var btnHome   : ImageView
    var soalsekarang = 0
    var jawabanSatuSekarang = true
    var jawabanDuaSekarang = true
    var benar = 0
    var soal: List<List<Int>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_angka)
        initView()
        initClick()
        initSoal()
        setGambarKeSoal(soal!![soalsekarang][0],soal!![soalsekarang][1])
    }

    private fun initView(){
        soalSatu = findViewById(R.id.soalSatu)
        soalDua = findViewById(R.id.soalDua)
        jawabSatu = findViewById(R.id.jawabSatu)
        jawabDua = findViewById(R.id.jawabDua)
        panahSatuLeft = findViewById(R.id.panahSatuLeft)
        panahSatuRight = findViewById(R.id.panahSatuRight)
        panahDuaLeft = findViewById(R.id.panahDuaLeft)
        panahDuaRight = findViewById(R.id.panahDuaRight)
        lyInduk = findViewById(R.id.lyInduk)
        btnBack = findViewById(R.id.btnBack)
        btnHome = findViewById(R.id.btnHome)
        btnNext = findViewById(R.id.btnNext)
        contentUtama = findViewById(R.id.contentUtama)
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
        btnNext.setOnClickListener {
            if(soalsekarang < 5){

                if(soalsekarang==4){
                    cekJawaban(soal!![soalsekarang][0],soal!![soalsekarang][1])
                }else{
                    cekJawaban(soal!![soalsekarang][0],soal!![soalsekarang][1])
                    soalsekarang++
                    setGambarKeSoal(soal!![soalsekarang][0],soal!![soalsekarang][1])
                    jawabanSatuSekarang = true
                    jawabanDuaSekarang = true
                    jawabSatu.text = "0"
                    jawabDua.text = "0"
                }
            }else{
                btnNext.visibility = View.GONE
            }
        }
        panahSatuLeft.setOnClickListener {
            setJawabanSatuKeSoal(soal!![soalsekarang][0],soal!![soalsekarang][1])
        }
        panahSatuRight.setOnClickListener {
            setJawabanSatuKeSoal(soal!![soalsekarang][0],soal!![soalsekarang][1])
        }
        panahDuaLeft.setOnClickListener {
            setJawabanDuaKeSoal(soal!![soalsekarang][0],soal!![soalsekarang][1])
        }
        panahDuaRight.setOnClickListener {
            setJawabanDuaKeSoal(soal!![soalsekarang][0],soal!![soalsekarang][1])
        }
    }
    private fun initSoal(){
        soal= generateRandomAngka()
        Log.d("soal",soal.toString())
    }
    private fun setGambarKeSoal(n1:Int,n2:Int){
        val gambar1 = getGambar(n1,this)
        //gunakan glide untuk mengubah soal1
        Glide.with(this)
            .load(gambar1)
            .into(soalSatu)
        val gambar2 = getGambar(n2,this)
        //gunakan glide untuk mengubah soal2
        Glide.with(this)
            .load(gambar2)
            .into(soalDua)
    }
    private fun setJawabanSatuKeSoal(n1:Int,n2:Int){
        if (jawabanSatuSekarang){
            jawabSatu.text = n1.toString()
            jawabanSatuSekarang = false
        }else{
            jawabSatu.text = n2.toString()
            jawabanSatuSekarang=true
        }
    }
    private fun setJawabanDuaKeSoal(n1:Int,n2:Int){
        if (jawabanDuaSekarang){
            jawabDua.text = n1.toString()
            jawabanDuaSekarang=false
        }else{
            jawabDua.text = n2.toString()
            jawabanDuaSekarang=true
        }
    }
    private fun cekJawaban(n1:Int,n2:Int){
        if(jawabSatu.text == n1.toString() && jawabDua.text == n2.toString()){
            benar++
            addRating(lyInduk,this)
            showSnackbar(contentUtama,"Benar")
        }else{
            showSnackbar(contentUtama,"Salah")
        }
    }
}