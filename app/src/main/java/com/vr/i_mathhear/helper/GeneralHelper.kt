package com.vr.i_mathhear.helper

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.vr.i_mathhear.R
import java.util.regex.Pattern
import kotlin.random.Random

// Fungsi untuk mengambil warna acak dari XML sumber daya
fun getRandomColor(context: Context): Int {
    val colorArray = arrayOf(
        R.color.black,
        R.color.white,
        R.color.biruMuda,
        R.color.hijauMuda,
        R.color.unguMuda,
        R.color.merahMuda,
        R.color.kuningMuda,
        R.color.biruTua,
        R.color.hijauTua,
        R.color.unguTua,
        R.color.merahTua,
        R.color.kuningTua,
        R.color.biru,
        R.color.hijau,
        R.color.ungu,
        R.color.merah,
        R.color.kuning,
        R.color.biruGelap,
        R.color.hijauGelap,
        R.color.unguGelap,
        R.color.merahGelap,
        R.color.kuningGelap
    )

    // Ambil satu warna acak dari array
    val randomIndex = Random.nextInt(colorArray.size)
    val randomColorResId = colorArray[randomIndex]

    // Dapatkan warna aktual dari sumber daya
    return getColorFromResource(context.resources, randomColorResId)
}

// Fungsi untuk mengambil warna dari sumber daya berdasarkan ID
private fun getColorFromResource(resources: Resources, resId: Int): Int {
    return try {
        resources.getColor(resId, null)
    } catch (e: Resources.NotFoundException) {
        // Penanganan jika ID warna tidak ditemukan
        Color.BLACK // Warna default jika tidak ditemukan
    }
}

fun extractVideoIdFromUrl(videoUrl: String): String? {
    val pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2Fvideos%2F|youtu.be%2F|v%2F)[^#\\&\\?\\n]*"
    val compiledPattern = Pattern.compile(pattern)
    val matcher = compiledPattern.matcher(videoUrl)

    return if (matcher.find()) {
        matcher.group()
    } else {
        null
    }
}

fun animateClick(view: View){
    view.animate().scaleX(0.9f).scaleY(0.9f).duration = 100
    view.animate().scaleX(1f).scaleY(1f).duration = 100
}
fun generateRandomAngka(): List<List<Int>> {
    val random = Random.Default
    val result = mutableListOf<List<Int>>()

    for (i in 1..5) {
        val subarray = mutableListOf<Int>()
        while (subarray.size < 2) {
            val randomNum = random.nextInt(0, 10)
            if (!subarray.contains(randomNum)) {
                subarray.add(randomNum)
            }
        }
        result.add(subarray)
    }

    return result
}

fun addRating(induk: LinearLayout, context: Context) {
    val imageView = ImageView(induk.context)
    val layoutParams = LinearLayout.LayoutParams(
        context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._20sdp),
        context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._20sdp)
    )

    imageView.layoutParams = layoutParams
    imageView.setBackgroundResource(R.drawable.ic_star_full)
    imageView.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(induk.context, R.color.kuningTua))

    induk.addView(imageView)
}

fun getGambar(nomor: Int, context: Context): Drawable? {
    val resourceName = "sign_language_0$nomor"
    val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
    return if (resourceId != 0) {
        ContextCompat.getDrawable(context, resourceId)
    } else {
        null
    }
}
fun getGambarRandom(context: Context): Drawable? {
    val gambarNames = listOf("apel", "ayam", "bebek", "itik", "jeruk")
    val randomIndex = Random.nextInt(gambarNames.size)
    val resourceName = gambarNames[randomIndex]

    val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
    return if (resourceId != 0) {
        ContextCompat.getDrawable(context, resourceId)
    } else {
        null
    }
}
fun showSnackbar(view: View, message: String) {
    val context = view.context // Ambil context dari view
    val snackbarLayout = View.inflate(context, R.layout.ly_snackbar, null) // Inflate layout Snackbar
    val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
    snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.biruMuda))
    snackbar.view.setPadding(0, 0, 0, 0) // Menghapus padding
    (snackbar.view as Snackbar.SnackbarLayout).addView(snackbarLayout, 0) // Menambahkan layout di atas
    snackbarLayout.findViewById<TextView>(R.id.snackbar_text).text = message // Mengatur pesan di dalam layout Snackbar
    snackbar.show()
}
data class HitungData(val angka1: Int, val angka2: Int, val hasil: Int, val drawable: Drawable?)

fun generateRandomHitung(context: Context): List<HitungData> {
    val result = mutableListOf<HitungData>()

    for (level in 1..3) {
        var num1: Int
        var num2: Int
        var hasil: Int
        do {
            num1 = Random.nextInt(1, 11)
            num2 = generateRandomNumber(num1)
            hasil = num1 + num2
        } while (result.any { it.hasil == hasil })

        val gambar = getGambarRandom(context)
        result.add(HitungData(num1, num2, hasil, gambar))
    }

    for (level in 4..5) {
        val num1 = Random.nextInt(1, 11)
        val num2 = generateRandomNumber(num1)
        val gambar = getGambarRandom(context)
        val hasil = if (level <= 3) num1 + num2 else num1 - num2
        result.add(HitungData(num1, num2, hasil, gambar))
    }

    return result
}

fun generateRandomNumber(num1: Int): Int {
    val maxNum2 = minOf(10 - num1, num1 - 1)
    return Random.nextInt(1, maxNum2 + 1)
}

fun tambahkanGambarKeLinearLayout(context: Context, lySoal: LinearLayout, drawable: Drawable, jumlahGambar: Int) {
    // Maksimal 5 gambar per LinearLayout
    val maksimalGambarPerLinearLayout = 5
    var currentLinearLayout: LinearLayout? = null

    for (i in 0 until jumlahGambar) {
        if (i % maksimalGambarPerLinearLayout == 0) {
            // Jika gambar ke-6, ke-11, dst., buat LinearLayout baru
            currentLinearLayout = LinearLayout(context)
            currentLinearLayout.orientation = LinearLayout.HORIZONTAL
            lySoal.addView(currentLinearLayout)
        }

        val imageView = ImageView(context)
        imageView.layoutParams = LinearLayout.LayoutParams(
            context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._26sdp),
            context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._26sdp)
        )
        imageView.background = drawable

        currentLinearLayout?.addView(imageView)
    }
}
fun generateAnswerOptions(kunci: Int): List<Int> {
    val opsi = mutableListOf(kunci)
    while (opsi.size < 4) {
        val randomOption = (1..10).random()
        if (randomOption != kunci && !opsi.contains(randomOption)) {
            opsi.add(randomOption)
        }
    }

    opsi.shuffle() // Mengacak urutan opsi

    val kunciIndex = opsi.indexOf(kunci)
    opsi[kunciIndex] = kunci // Menggantikan salah satu opsi dengan kunci

    val penanda = kunciIndex
    opsi.add(penanda)

    return opsi
}




