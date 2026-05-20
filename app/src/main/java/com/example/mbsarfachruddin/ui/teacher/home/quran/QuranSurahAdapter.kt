package com.example.mbsarfachruddin.ui.teacher.home.quran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemQuranSurahBinding
import com.example.mbsarfachruddin.model.local.quran.surah.Data
import dev.androidbroadcast.vbpd.viewBinding

class QuranSurahAdapter(private var listSurah: List<Data>) : RecyclerView.Adapter<QuranSurahAdapter.QuranSurahViewHolder>() {

    var itemClickListener: ((Data) -> Unit)? = null

    inner class QuranSurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemQuranSurahBinding by viewBinding(ItemQuranSurahBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranSurahViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_quran_surah, parent, false)
        return QuranSurahViewHolder(view)
    }

    override fun getItemCount(): Int = listSurah.size

    override fun onBindViewHolder(holder: QuranSurahViewHolder, position: Int) {
        val surah = listSurah[position]
        with(holder.binding) {
            tvSurahNo.text = surah.nomor.toString()
            tvSurahName.text = surah.namaLatin
            tvSurahDesc.text = "${surah.arti} - ${surah.jumlahAyat} Ayat"
            tvSurahNameAr.text = surah.nama
        }
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(surah)
        }
    }

    fun updateData(newList: List<Data>) {
        listSurah = newList
        notifyDataSetChanged()
    }
}