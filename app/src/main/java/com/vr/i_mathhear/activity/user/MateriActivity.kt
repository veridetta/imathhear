package com.vr.i_mathhear.activity.user

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.vr.i_mathhear.R
import com.vr.i_mathhear.adapter.MateriAdapter
import com.vr.i_mathhear.model.MateriModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MateriActivity : AppCompatActivity() {
    private val mFirestore = FirebaseFirestore.getInstance()
    private lateinit var materiAdapter: MateriAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnBack: LinearLayout
    val TAG = "LOAD DATA"
    private val materiList: MutableList<MateriModel> = mutableListOf()
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_materi)
        initProgress()
        initView()
        readData(mFirestore)
    }
    private fun initView(){
        recyclerView = findViewById(R.id.rcMateri)
        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MateriActivity, 3)
            // set the custom adapter to the RecyclerView
            materiAdapter = MateriAdapter(
                materiList,
                this@MateriActivity,
            ) { materi -> clickMateri(materi) }
            adapter = materiAdapter
        }
    }
    private fun initProgress(){
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Mengambil data...")
        progressDialog.setCancelable(false) // Setel agar ProgressDialog tidak dapat dibatalkan oleh pengguna
    }
    private fun clickMateri(materi: MateriModel) {
        val intent = Intent(this, DetailMateriActivity::class.java)
        intent.putExtra("nama", materi.nama)
        intent.putExtra("url", materi.urlYoutube)
        startActivity(intent)
    }
    private fun readData(db: FirebaseFirestore) {
        progressDialog.show() // Tampilkan ProgressDialog
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = db.collection("materi").get().await()
                val materis = mutableListOf<MateriModel>()
                for (document in result) {
                    val materi = document.toObject(MateriModel::class.java)
                    val docId = document.id
                    materi.documentId = docId
                    materis.add(materi)
                    Log.d(TAG, "Datanya : ${document.id} => ${document.data}")
                }

                withContext(Dispatchers.Main) {
                    materiList.addAll(materis)
                    materiAdapter.filteredBarangList.addAll(materis)
                    materiAdapter.notifyDataSetChanged()
                    progressDialog.dismiss() // Sembunyikan ProgressDialog setelah selesai
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error getting documents : $e")
                progressDialog.dismiss() // Sembunyikan ProgressDialog jika terjadi kesalahan
            }
        }
    }

}