package com.example.mbsarfachruddin.ui.teacher.home.presence

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentTeacherPresenceBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TeacherPresenceFragment : Fragment(R.layout.fragment_teacher_presence) {

    private val binding: FragmentTeacherPresenceBinding by viewBinding(FragmentTeacherPresenceBinding::bind)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            title = "Pilih Jadwal"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        val nip = TinyDB(requireContext()).getString("user_id")

        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern(
            "EEEE, dd/MM/yyyy", Locale("id", "ID")
        )
        val dateFormat = date.format(formatter)
        binding.tvDate.text = dateFormat

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val sdf = SimpleDateFormat("EEEE", Locale("id", "ID"))
            val day = sdf.format(Date())
            val response = apiService.getTeacherSchedule(nip, day)

            if (response.status) {
                val adapter = ScheduleAdapter(response.data)
                binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
                binding.rvSchedule.adapter = adapter
                adapter.itemClickListener = { data ->
                    val bundle = Bundle().apply {
                        putString("class_id", data.classId)
                        putString("course_id", data.courseId)
                    }
                    findNavController().navigate(R.id.action_teacherPresenceFragment_to_teacherPresenceDetailFragment, bundle)
                }
            }
        }
    }

}