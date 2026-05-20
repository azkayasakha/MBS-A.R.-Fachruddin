package com.example.mbsarfachruddin.ui.student.home.presence

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemStudentPresenceLessonBinding
import com.example.mbsarfachruddin.model.remote.student.courseattendance.Data
import dev.androidbroadcast.vbpd.viewBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PresenceLessonAdapter(private val listPresence: List<com.example.mbsarfachruddin.model.remote.student.courseattendance.Data>) : RecyclerView.Adapter<PresenceLessonAdapter.PresenceViewHolder>() {
    inner class PresenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemStudentPresenceLessonBinding by viewBinding(ItemStudentPresenceLessonBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenceViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_student_presence_lesson, parent, false)
        return PresenceViewHolder(view)
    }

    override fun getItemCount(): Int = listPresence.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PresenceViewHolder, position: Int) {
        val presence = listPresence[position]

        with (holder.binding) {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val dateTime = LocalDateTime.parse(presence.createdAt, inputFormatter)
            val result = dateTime.format(outputFormatter)
            tvDate.text = result
            tvStatus.text = presence.status
        }
    }
}