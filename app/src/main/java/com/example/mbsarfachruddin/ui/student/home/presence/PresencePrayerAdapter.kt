package com.example.mbsarfachruddin.ui.student.home.presence

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemStudentPresencePrayerBinding
import com.example.mbsarfachruddin.model.remote.student.prayerattendance.Data
import dev.androidbroadcast.vbpd.viewBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PresencePrayerAdapter(private val listPresence: List<Data>): RecyclerView.Adapter<PresencePrayerAdapter.PresenceViewHolder>(){
    inner class PresenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemStudentPresencePrayerBinding by viewBinding(ItemStudentPresencePrayerBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresencePrayerAdapter.PresenceViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_student_presence_prayer, parent, false)
        return PresenceViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PresencePrayerAdapter.PresenceViewHolder, position: Int) {
        val presence = listPresence[position]

        with(holder.binding) {
            val parsedDate = LocalDate.parse(presence.date) // ISO-8601 by default
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formatted = parsedDate.format(outputFormatter)
            tvDate.text = formatted

            tvPrayerShubuh.text = presence.attendance.subuh
            tvPrayerDzuhur.text = presence.attendance.dzuhur
            tvPrayerAshar.text = presence.attendance.ashar
            tvPrayerMaghrib.text = presence.attendance.maghrib
            tvPrayerIsya.text = presence.attendance.isya
        }
    }

    override fun getItemCount(): Int = listPresence.size
}