package com.example.mbsarfachruddin.ui.musyrif.home.tahfidz

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemMusyrifTahfidzBinding
import com.example.mbsarfachruddin.model.local.quran.surah.SurahResponse
import com.example.mbsarfachruddin.model.remote.musyrif.tahfidz.Data
import com.google.gson.Gson
import dev.androidbroadcast.vbpd.viewBinding
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.core.graphics.toColorInt

class TahfidzAdapter(private val listTahfidz: List<Data>): RecyclerView.Adapter<TahfidzAdapter.TahfidzViewHolder>() {

    var itemClickListener: ((Data) -> Unit)? = null

    inner class TahfidzViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMusyrifTahfidzBinding by viewBinding(ItemMusyrifTahfidzBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TahfidzViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_musyrif_tahfidz, parent, false)
        return TahfidzViewHolder(view)
    }

    override fun getItemCount(): Int = listTahfidz.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TahfidzViewHolder, position: Int) {
        val tahfidz = listTahfidz[position]

        with(holder.binding) {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val dateTime = LocalDateTime.parse(tahfidz.createdAt, inputFormatter)
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")
            tvDate.text = dateTime.format(outputFormatter)

            if (tahfidz.isChecked.toBoolean()) {
                ivCheck.setImageResource(R.drawable.ic_check_true)
                ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf("#4CAF50".toColorInt()))
            } else {
                ivCheck.setImageResource(R.drawable.ic_check_false)
                ImageViewCompat.setImageTintList(ivCheck, ColorStateList.valueOf("#808080".toColorInt()))
            }

            tvPage.text = "Juz ${tahfidz.quranJuz} - Hal. ${tahfidz.quranPage}"
            when (tahfidz.quranPageSection) {
                0.25 -> tvPageSection.text = "1/4 Halaman"
                0.5 -> tvPageSection.text = "1/2 Halaman"
                1.0 -> tvPageSection.text = "1 Halaman"
                else -> tvPageSection.text = "-"
            }

            tvTahfidzType.text = tahfidz.type

            ivDelete.setOnClickListener {
                itemClickListener?.invoke(tahfidz)
            }
        }
    }

    private fun getSurahByNumber(number: Int, context: Context): String? {
        val surahList = readSurahFromAssets(context)
        return surahList?.find { it.nomor == number }?.namaLatin
    }

    private fun readSurahFromAssets(context: Context): List<com.example.mbsarfachruddin.model.local.quran.surah.Data>? {
        try {
            val inputStream = context.assets.open("surah.json")
            val reader = InputStreamReader(inputStream)
            val response = Gson().fromJson(reader, SurahResponse::class.java)
            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}

private fun Int.toBoolean() = this == 1
