package com.vr.i_mathhear.adapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vr.i_mathhear.R
import com.vr.i_mathhear.helper.getRandomColor
import com.vr.i_mathhear.model.MateriModel
import java.util.Locale


class MateriAdapter(
    private var barangList: MutableList<MateriModel>,
    val context: Context,
    private val onCardClickListener: (MateriModel) -> Unit,
) : RecyclerView.Adapter<MateriAdapter.ProductViewHolder>() {
    public var filteredBarangList: MutableList<MateriModel> = mutableListOf()
    init {
        filteredBarangList.addAll(barangList)
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && filteredBarangList.isEmpty()) {
            1 // Return 1 for empty state view
        } else {
            0 // Return 0 for regular product view
        }
    }
    fun filter(query: String) {
        filteredBarangList.clear()
        if (query !== null || query !=="") {
            val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
            for (product in barangList) {
                val nam = product.nama?.toLowerCase(Locale.getDefault())?.contains(lowerCaseQuery)
                Log.d("Kunci ", lowerCaseQuery)
                if (nam == true) {
                    filteredBarangList.add(product)
                    Log.d("Ada ", product.nama.toString())
                }
            }
        } else {
            filteredBarangList.addAll(barangList)
        }
        notifyDataSetChanged()
        Log.d("Data f",filteredBarangList.size.toString())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_materi, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filteredBarangList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentBarang = filteredBarangList[position]
        holder.tvMateri.text = currentBarang.nama
        //ganti color dari lyColor ambil dari fungsi getRandomColor
        holder.lyColor.setBackgroundColor(getRandomColor(context))
        holder.btnMateri.setOnClickListener { onCardClickListener(currentBarang) }
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMateri: TextView = itemView.findViewById(R.id.tvMateri)
        val btnMateri: CardView = itemView.findViewById(R.id.btnMateri)
        val lyColor: LinearLayout = itemView.findViewById(R.id.lyColor)
    }
}
