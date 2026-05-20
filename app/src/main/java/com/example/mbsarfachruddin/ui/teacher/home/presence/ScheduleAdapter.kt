package com.example.mbsarfachruddin.ui.teacher.home.presence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemTeacherScheduleBinding
import com.example.mbsarfachruddin.model.remote.teacher.schedule.Data
import dev.androidbroadcast.vbpd.viewBinding

class ScheduleAdapter(private val listSchedule: List<Data>): RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    var itemClickListener: ((Data) -> Unit)? = null

    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemTeacherScheduleBinding by viewBinding(ItemTeacherScheduleBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_teacher_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun getItemCount(): Int = listSchedule.size

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = listSchedule[position]

        with(holder.binding) {
            tvSubject.text = "${schedule.subject} ${schedule.classId.uppercase()}"
            tvTime.text = "${schedule.startTime.substring(0, 5)} - ${schedule.endTime.substring(0, 5)}"
        }
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(schedule)
        }
    }
}