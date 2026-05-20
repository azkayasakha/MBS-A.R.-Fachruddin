package com.example.mbsarfachruddin.ui.musyrif.home.tahfidz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemMusyrifTahfidzNameBinding
import com.example.mbsarfachruddin.model.remote.musyrif.halaqahmember.Data
import dev.androidbroadcast.vbpd.viewBinding

class StudentAdapter(private val listStudent: List<Data>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    var itemClickListener: ((Data) -> Unit)? = null

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMusyrifTahfidzNameBinding by viewBinding(ItemMusyrifTahfidzNameBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_musyrif_tahfidz_name, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int = listStudent.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = listStudent[position]

        holder.binding.tvName.text = student.name
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(student)
        }
    }
}