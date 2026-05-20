package com.example.mbsarfachruddin.ui.musyrif.announcement

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemAnnouncementBinding
import com.example.mbsarfachruddin.model.remote.announcement.Data
import dev.androidbroadcast.vbpd.viewBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AnnouncementAdapter(private val listAnnouncement: List<Data>): RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>() {
    inner class AnnouncementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemAnnouncementBinding by viewBinding(ItemAnnouncementBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementAdapter.AnnouncementViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_announcement, parent, false)
        return AnnouncementViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AnnouncementAdapter.AnnouncementViewHolder, position: Int) {
        val announcement = listAnnouncement[position]
        with(holder.binding) {
            var role = ""
            when (announcement.role) {
                "Student" -> role = "Orang Tua"
                "Musyrif" -> role = "Musyrif"
                "Teacher" -> role = "Guru"
                "All" -> role = "Semua"
            }
            tvRole.text = role

            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern(
                "dd MMMM yyyy, HH:mm",
                Locale("id", "ID")
            )
            val dateTime = LocalDateTime.parse(announcement.createdAt, inputFormatter)
            tvDate.text = dateTime.format(outputFormatter)

            tvTitle.text = announcement.title
            tvContent.text = announcement.content
        }
    }

    override fun getItemCount(): Int = listAnnouncement.size
}