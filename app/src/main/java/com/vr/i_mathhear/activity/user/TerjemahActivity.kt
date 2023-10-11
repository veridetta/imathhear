package com.vr.i_mathhear.activity.user

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.vr.i_mathhear.R
import android.os.CountDownTimer
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.constraintlayout.widget.ConstraintLayout
import com.vr.i_mathhear.helper.showSnackbar
import java.util.Locale
import java.util.Objects


class TerjemahActivity : AppCompatActivity() {
    lateinit var btnBack: LinearLayout
    lateinit var btnRekam: CardView
    lateinit var contentView: ConstraintLayout

    private lateinit var textToSpeech: TextToSpeech
    private val REQUEST_CODE_SPEECH_INPUT = 1
    var hasil = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_terjemah)
        initView()
        initClick()
    }

    private fun initView() {
        btnBack = findViewById(R.id.btnBack)
        btnRekam = findViewById(R.id.btnRekam)
        contentView = findViewById(R.id.contentView)

    }

    private fun initClick() {
        btnBack.setOnClickListener {
            finish()
        }

        btnRekam.setOnClickListener {
            initMic()
        }
    }
    private fun initMic(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        intent.putExtra(
            // Bahasa Indonesia
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale("id", "ID")
        )

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            showSnackbar(contentView,e.message.toString())
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                hasil = Objects.requireNonNull(res)[0]
                val intent = Intent(this, HasilTerjemahActivity::class.java)
                intent.putExtra("hasil",hasil)
                startActivity(intent)
            }
        }
    }
}
