package com.vr.i_mathhear.activity.admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.vr.i_mathhear.R
import com.vr.i_mathhear.helper.showSnackbar
import java.util.UUID

class TambahActivity : AppCompatActivity() {
    lateinit var contentView:RelativeLayout
    lateinit var btnSimpan:Button
    lateinit var etName:EditText
    lateinit var etUrl:EditText
    lateinit var tvJudul:TextView
    lateinit var progressDialog: ProgressDialog
    var mFirestore = FirebaseFirestore.getInstance()
    var type = ""
    var name = ""
    var url = ""
    var docId = ""
    var uid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_tambah)
        initView()
        initIntent()
        setIntent()
        initClick()
    }
    private fun initView(){
        contentView = findViewById(R.id.contentView)
        btnSimpan = findViewById(R.id.btnSimpan)
        etName = findViewById(R.id.etName)
        etUrl = findViewById(R.id.etUrl)
        tvJudul = findViewById(R.id.tvJudul)

    }
    private fun initIntent(){
        type = intent.getStringExtra("type").toString()
        name = intent.getStringExtra("materi_nama").toString()
        url = intent.getStringExtra("materi_urlYoutube").toString()
        docId = intent.getStringExtra("materi_documentId").toString()
        uid = intent.getStringExtra("materi_uid").toString()
    }
    private fun setIntent(){
        if (type == "tambah"){
            tvJudul.text = "Tambah Materi"
            btnSimpan.text = "Tambah"
        }else{
            tvJudul.text = "Edit Materi"
            btnSimpan.text = "Simpan"
            etName.setText(name)
            etUrl.setText(url)
        }
    }
    private fun initClick(){
        btnSimpan.setOnClickListener {
            if (type == "tambah"){
                progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Menambahkan materi...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                tambahMateri()
            }else{
                progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Mengedit materi...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                editMateri()
            }
        }
    }
    private fun tambahMateri(){
        val name = etName.text.toString()
        val url = etUrl.text.toString()
        if (name.isEmpty()){
            etName.error = "Nama tidak boleh kosong"
            etName.requestFocus()
            return
        }
        if (url.isEmpty()){
            etUrl.error = "Url tidak boleh kosong"
            etUrl.requestFocus()
            return
        }
        val data = hashMapOf(
            "uid" to UUID.randomUUID().toString(),
            "nama" to name,
            "urlYoutube" to url
        )
        mFirestore.collection("materi").add(data)
            .addOnSuccessListener {
                progressDialog.dismiss()
                showSnackbar(contentView, "Berhasil menambahkan materi")
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                showSnackbar(contentView, "Gagal menambahkan materi")
            }
    }
    private fun editMateri(){
        val name = etName.text.toString()
        val url = etUrl.text.toString()
        if (name.isEmpty()){
            etName.error = "Nama tidak boleh kosong"
            etName.requestFocus()
            return
        }
        if (url.isEmpty()){
            etUrl.error = "Url tidak boleh kosong"
            etUrl.requestFocus()
            return
        }
        val data = hashMapOf(
            "uid" to uid,
            "nama" to name,
            "urlYoutube" to url
        )
        mFirestore.collection("materi").document(docId).update(data as Map<String, Any>)
            .addOnSuccessListener {
                progressDialog.dismiss()
                showSnackbar(contentView, "Berhasil mengedit materi")
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                showSnackbar(contentView, "Gagal mengedit materi")
            }
    }
}