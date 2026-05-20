package com.example.mbsarfachruddin.ui.teacher.home.presence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemTeacherPresenceBinding
import com.example.mbsarfachruddin.model.remote.teacher.courseattendance.Data
import dev.androidbroadcast.vbpd.viewBinding

class PresenceAdapter(private val listAttendance: List<Data>): RecyclerView.Adapter<PresenceAdapter.PresenceViewHolder>() {
    inner class PresenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemTeacherPresenceBinding by viewBinding(ItemTeacherPresenceBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenceAdapter.PresenceViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_teacher_presence, parent, false)
        return PresenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PresenceAdapter.PresenceViewHolder, position: Int) {
        val attendance = listAttendance[position]

        with(holder.binding) {
            tvName.text = attendance.studentName

            rgStatus.setOnCheckedChangeListener(null)
            when (attendance.status) {
                "Hadir" -> rgStatus.check(rbStatusPresent.id)
                "Sakit" -> rgStatus.check(rbStatusSick.id)
                "Izin" -> rgStatus.check(rbStatusLeave.id)
                "Alpa" -> rgStatus.check(rbStatusAbsent.id)
                else -> rgStatus.clearCheck()
            }
            rgStatus.setOnCheckedChangeListener { _, checkedId ->
                attendance.status = when (checkedId) {
                    rbStatusPresent.id -> "Hadir"
                    rbStatusSick.id -> "Sakit"
                    rbStatusLeave.id -> "Izin"
                    rbStatusAbsent.id -> "Alpa"
                    else -> null
                }
            }
        }
    }

    override fun getItemCount(): Int = listAttendance.size
}