package com.vr.i_mathhear.activity.admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.vr.i_mathhear.MainActivity
import com.vr.i_mathhear.R
import com.vr.i_mathhear.adapter.AdminMateriAdapter
import com.vr.i_mathhear.adapter.MateriAdapter
import com.vr.i_mathhear.helper.showSnackbar
import com.vr.i_mathhear.model.MateriModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AdminActivity : AppCompatActivity() {
    lateinit var contentView:CoordinatorLayout
    lateinit var btnLogout:LinearLayout
    lateinit var btnTambah:Button
    lateinit var rcMateri:RecyclerView
    private val mFirestore = FirebaseFirestore.getInstance()
    private lateinit var materiAdapter: AdminMateriAdapter
    lateinit var progressDialog: ProgressDialog
    val TAG = "LOAD DATA"
    private val materiList: MutableList<MateriModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_admin)
        initView()
        initClick()
        initRC()
    }
    private fun initView(){
        contentView = findViewById(R.id.contentView)
        btnLogout = findViewById(R.id.btnLogout)
        btnTambah = findViewById(R.id.btnTambah)
        rcMateri = findViewById(R.id.rcMateri)
    }
    private fun initClick() {
        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnTambah.setOnClickListener {
            val intent = Intent(this, TambahActivity::class.java)
            intent.putExtra("type", "tambah")
            startActivity(intent)
        }
    }
    private fun initRC(){
        rcMateri.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@AdminActivity, 1)
            // set the custom adapter to the RecyclerView
            materiAdapter = AdminMateriAdapter(
                materiList,
                this@AdminActivity,
                { materi -> editMateri(materi) },
                { materi -> hapusMateri(materi) }
            )
        }
        //progressdialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        getData()
        rcMateri.adapter = materiAdapter
    }
    private fun getData(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = mFirestore.collection("materi").get().await()
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
                    progressDialog.dismiss()
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error getting documents : $e")
                withContext(Dispatchers.Main) {
                    progressDialog.dismiss()
                }
            }
        }
    }
    private fun editMateri(materi: MateriModel) {
        val intent = Intent(this, TambahActivity::class.java)
        intent.putExtra("type", "edit")
        intent.putExtra("materi_nama", materi.nama)
        intent.putExtra("materi_documentId", materi.documentId)
        intent.putExtra("materi_urlYoutube", materi.urlYoutube)
        intent.putExtra("materi_uid", materi.uid)
        startActivity(intent)
    }
    private fun hapusMateri(materi: MateriModel) {
        val docId = materi.documentId.toString()
        mFirestore.collection("materi").document(docId).delete()
            .addOnSuccessListener {
                materiList.remove(materi)
                materiAdapter.notifyDataSetChanged()
                showSnackbar(contentView, "Materi berhasil dihapus")
            }
            .addOnFailureListener {
                showSnackbar(contentView, "Materi gagal dihapus")
            }
    }
}