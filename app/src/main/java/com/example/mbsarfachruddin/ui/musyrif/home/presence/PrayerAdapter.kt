package com.example.mbsarfachruddin.ui.musyrif.home.presence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemMusyrifPresenceBinding
import com.example.mbsarfachruddin.model.remote.musyrif.prayerattendance.Data
import dev.androidbroadcast.vbpd.viewBinding

class PrayerAdapter(private val listAttendance: List<Data>): RecyclerView.Adapter<PrayerAdapter.PrayerViewHolder>() {
    inner class PrayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMusyrifPresenceBinding by viewBinding(ItemMusyrifPresenceBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerAdapter.PrayerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_musyrif_presence, parent, false)
        return PrayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrayerAdapter.PrayerViewHolder, position: Int) {
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