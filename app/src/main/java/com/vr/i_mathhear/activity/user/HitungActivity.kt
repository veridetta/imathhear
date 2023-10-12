package com.vr.i_mathhear.activity.user

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.vr.i_mathhear.MainActivity
import com.vr.i_mathhear.R
import com.vr.i_mathhear.helper.HitungData
import com.vr.i_mathhear.helper.addRating
import com.vr.i_mathhear.helper.generateAnswerOptions
import com.vr.i_mathhear.helper.generateRandomHitung
import com.vr.i_mathhear.helper.showSnackbar
import com.vr.i_mathhear.helper.tambahkanGambarKeLinearLayout

class HitungActivity : AppCompatActivity() {
    lateinit var contentUtama   : ConstraintLayout
    lateinit var btnBack   : LinearLayout
    lateinit var btnNext   : LinearLayout
    lateinit var btnHome   : ImageView
    lateinit var lySatu   : LinearLayout
    lateinit var lyInduk   : LinearLayout
    lateinit var lyDua   : LinearLayout
    lateinit var btnOpsiA   : LinearLayout
    lateinit var lyOpsiA   : LinearLayout
    lateinit var btnOpsiB   : LinearLayout
    lateinit var lyOpsiB   : LinearLayout
    lateinit var btnOpsiC   : LinearLayout
    lateinit var lyOpsiC   : LinearLayout
    lateinit var btnOpsiD   : LinearLayout
    lateinit var lyOpsiD   : LinearLayout
    lateinit var tvOperasi   : TextView
    lateinit var tvBtnNext   : TextView
    var soalsekarang = 0
    var benar = 0
    var kunci = 0
    var jawaban = 0
    var opsiA = 0
    var opsiB=0
    var opsiC=0
    var opsiD=0
    lateinit var lyJawab : LinearLayout
    private lateinit var soal: List<HitungData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_hitung)
        initView()
        initClick()
        initSoal()
    }
    private fun initView(){
        contentUtama = findViewById(R.id.contentUtama)
        btnBack = findViewById(R.id.btnBack)
        btnNext = findViewById(R.id.btnNext)
        btnHome = findViewById(R.id.btnHome)
        lySatu = findViewById(R.id.lySatu)
        lyDua = findViewById(R.id.lyDua)
        lyInduk = findViewById(R.id.lyInduk)
        btnOpsiA = findViewById(R.id.btnOpsiA)
        lyOpsiA = findViewById(R.id.lyOpsiA)
        btnOpsiB = findViewById(R.id.btnOpsiB)
        lyOpsiB = findViewById(R.id.lyOpsiB)
        btnOpsiC = findViewById(R.id.btnOpsiC)
        lyOpsiC = findViewById(R.id.lyOpsiC)
        btnOpsiD = findViewById(R.id.btnOpsiD)
        lyOpsiD = findViewById(R.id.lyOpsiD)
        tvOperasi = findViewById(R.id.tvOperasi)
        tvBtnNext = findViewById(R.id.tvBtnNext)

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
                    tvBtnNext.text = "Selesai"
                    cekJawaban(lyJawab)
                    soalsekarang++
                }else{
                    cekJawaban(lyJawab)
                    soalsekarang++
                    kunci = 0
                    jawaban = 0
                    opsiA = 0
                    opsiB=0
                    opsiC=0
                    opsiD=0
                    setSoal(soal[soalsekarang].angka1,soal[soalsekarang].angka2,
                        soal[soalsekarang].hasil,soal[soalsekarang].drawable!!)
                    clearJawaban()
                    if(soalsekarang ==3){
                        tvBtnNext.text = "Periksa Jawaban"
                    }
                }
            }else {
                finish()
            }
        }
        btnOpsiA.setOnClickListener {
            jawaban = opsiA
            lyJawab = btnOpsiA
            clearJawaban()
            setJawaban(btnOpsiA)
        }
        btnOpsiB.setOnClickListener {
            jawaban = opsiB
            lyJawab = btnOpsiB
            clearJawaban()
            setJawaban(btnOpsiB)
        }
        btnOpsiC.setOnClickListener {
            jawaban = opsiC
            lyJawab = btnOpsiC
            clearJawaban()
            setJawaban(btnOpsiC)
        }
        btnOpsiD.setOnClickListener {
            jawaban = opsiD
            lyJawab = btnOpsiD
            clearJawaban()
            setJawaban(btnOpsiD)
        }
    }
    private fun initSoal(){
        soal = generateRandomHitung(this)
        Log.d("soal", soal.toString())
        setSoal(soal[soalsekarang].angka1,soal[soalsekarang].angka2,
            soal[soalsekarang].hasil,soal[soalsekarang].drawable!!)
    }
    private fun setSoal(n1:Int,n2:Int, kuncix:Int, gambar:Drawable){
        lySatu.removeAllViews()
        lyDua.removeAllViews()
        lyOpsiA.removeAllViews()
        lyOpsiB.removeAllViews()
        lyOpsiC.removeAllViews()
        lyOpsiD.removeAllViews()
        tambahkanGambarKeLinearLayout(this, lySatu, gambar, n1)
        tambahkanGambarKeLinearLayout(this, lyDua, gambar, n2)
        val opsi = generateAnswerOptions(kuncix)
        tambahkanGambarKeLinearLayout(this, lyOpsiA, gambar, opsi[0])
        tambahkanGambarKeLinearLayout(this, lyOpsiB, gambar, opsi[1])
        tambahkanGambarKeLinearLayout(this, lyOpsiC, gambar, opsi[2])
        tambahkanGambarKeLinearLayout(this, lyOpsiD, gambar, opsi[3])
        kunci = kuncix
        opsiA = opsi[0]
        opsiB = opsi[1]
        opsiC = opsi[2]
        opsiD = opsi[3]

        var operasi = "+"
        if(soalsekarang > 2){
            operasi = "-"
        }
        tvOperasi.text = operasi
    }
    private fun cekJawaban(ly : LinearLayout){
        if (jawaban == kunci){
            //ubah background jadi warna hijau
            ly.setBackgroundResource(R.color.hijauMuda)
            addRating(lyInduk,this)
            showSnackbar(contentUtama,"Benar")
            benar++
        }else{
            ly.setBackgroundResource(R.color.merahMuda)
            if (jawaban == opsiA){
                btnOpsiA.setBackgroundResource(R.color.hijauMuda)
            }else if(jawaban == opsiB){
                btnOpsiB.setBackgroundResource(R.color.hijauMuda)
            }else if(jawaban == opsiC){
                btnOpsiC.setBackgroundResource(R.color.hijauMuda)
            }else if(jawaban == opsiD){
                btnOpsiD.setBackgroundResource(R.color.hijauMuda)
            }
            showSnackbar(contentUtama,"Salah")
        }
    }
    private fun setJawaban(ly: LinearLayout){
        ly.setBackgroundResource(R.color.kuningMuda)
    }
    private fun clearJawaban(){
       //ubah btnOpsi ke @drawable/button_rounded
        btnOpsiA.setBackgroundResource(R.drawable.button_rounded)
        btnOpsiB.setBackgroundResource(R.drawable.button_rounded)
        btnOpsiC.setBackgroundResource(R.drawable.button_rounded)
        btnOpsiD.setBackgroundResource(R.drawable.button_rounded)
    }
}