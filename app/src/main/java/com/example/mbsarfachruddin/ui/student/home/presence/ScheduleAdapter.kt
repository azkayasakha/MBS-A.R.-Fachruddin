package com.example.mbsarfachruddin.ui.student.home.presence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemStudentScheduleBinding
import com.example.mbsarfachruddin.model.remote.student.classattendance.Data
import dev.androidbroadcast.vbpd.viewBinding

class ScheduleAdapter(private val listSchedule: List<Data>) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    var itemClickListener: ((Data) -> Unit)? = null

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemStudentScheduleBinding by viewBinding(ItemStudentScheduleBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_student_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun getItemCount(): Int = listSchedule.size

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = listSchedule[position]

        with(holder.binding) {
            tvSubject.text = schedule.subject
            tvTime.text = "• Jam ${schedule.startTime.substring(0, 5)} - ${schedule.endTime.substring(0, 5)}"
            tvStatus.text = "• Status: ${schedule.status}"
        }

        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(schedule)
        }
    }
}