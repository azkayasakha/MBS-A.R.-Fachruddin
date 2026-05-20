package com.example.mbsarfachruddin.ui.student.home.tahfidz

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemStudentTahfidzBinding
import com.example.mbsarfachruddin.model.local.quran.surah.SurahResponse
import com.example.mbsarfachruddin.model.remote.student.tahfidz.Data
import com.google.gson.Gson
import dev.androidbroadcast.vbpd.viewBinding
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TahfidzAdapter(private val listTahfidz: List<Data>, private val onCheckedChange: (Data, Boolean) -> Unit): RecyclerView.Adapter<TahfidzAdapter.TahfidzViewHolder>() {
    class TahfidzViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemStudentTahfidzBinding by viewBinding(ItemStudentTahfidzBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TahfidzAdapter.TahfidzViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_student_tahfidz, parent, false)
        return TahfidzViewHolder(view)
    }

    override fun getItemCount(): Int = listTahfidz.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TahfidzAdapter.TahfidzViewHolder, position: Int) {
        val tahfidz = listTahfidz[position]

        with (holder.binding) {
            val id = tahfidz.id

            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")
            val dateTime = LocalDateTime.parse(tahfidz.createdAt, inputFormatter)
            val result = dateTime.format(outputFormatter)
            tvDate.text = result

            tvPage.text = "Juz ${tahfidz.quranJuz} - Hal. ${tahfidz.quranPage}"
            when (tahfidz.quranPageSection) {
                0.25 -> tvPageSection.text = "1/4 Halaman"
                0.5 -> tvPageSection.text = "1/2 Halaman"
                0.75 -> tvPageSection.text = "3/4 Halaman"
                1.0 -> tvPageSection.text = "1 Halaman"
                else -> tvPageSection.text = ""
            }

            tvTahfidzType.text = tahfidz.type

            cbCheck.setOnCheckedChangeListener(null)
            cbCheck.isChecked = tahfidz.isChecked.toBoolean()
            // kalau sudah dicentang → disable
            cbCheck.isEnabled = !tahfidz.isChecked.toBoolean()
            cbCheck.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onCheckedChange(tahfidz, true)
                }
            }
        }
    }

    private fun Int.toBoolean(): Boolean = this != 0
}