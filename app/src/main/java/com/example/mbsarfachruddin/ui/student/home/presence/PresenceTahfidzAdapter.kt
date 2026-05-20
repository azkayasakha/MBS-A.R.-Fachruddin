package com.example.mbsarfachruddin.ui.student.home.presence

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemStudentPresenceTahfidzBinding
import com.example.mbsarfachruddin.model.remote.student.tahfidzattendance.Data
import dev.androidbroadcast.vbpd.viewBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PresenceTahfidzAdapter(private val listPresence: List<Data>): RecyclerView.Adapter<PresenceTahfidzAdapter.PresenceViewHolder>() {
    inner class PresenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemStudentPresenceTahfidzBinding by viewBinding(ItemStudentPresenceTahfidzBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenceTahfidzAdapter.PresenceViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_student_presence_tahfidz, parent, false)
        return PresenceViewHolder(view)
    }

    override fun getItemCount(): Int = listPresence.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PresenceViewHolder, position: Int) {
        val presence = listPresence[position]

        with(holder.binding) {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val date = LocalDate.parse(presence.date, inputFormatter)
            tvDate.text = date.format(outputFormatter)

            tvTahfidzMorning.text = presence.attendance.pagi
            tvTahfidzEvening.text = presence.attendance.sore
        }
    }
}