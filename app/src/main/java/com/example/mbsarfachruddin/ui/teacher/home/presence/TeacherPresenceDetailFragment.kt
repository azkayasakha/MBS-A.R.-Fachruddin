package com.example.mbsarfachruddin.ui.teacher.home.presence

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.FragmentTeacherPresenceDetailBinding
import com.example.mbsarfachruddin.model.remote.teacher.courseattendance.Data
import com.example.mbsarfachruddin.network.ApiService
import com.google.android.material.datepicker.MaterialDatePicker
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TeacherPresenceDetailFragment : Fragment(R.layout.fragment_teacher_presence_detail) {

    private val binding: FragmentTeacherPresenceDetailBinding by viewBinding(FragmentTeacherPresenceDetailBinding::bind)
    private lateinit var attendanceList: MutableList<Data>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            title = "Presensi Pelajaran"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        val courseId = arguments?.getString("course_id") ?: ""
        val classId = arguments?.getString("class_id") ?: ""
        var courseDate = LocalDate.now().toString()

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        binding.etDate.setText(LocalDate.now().format(formatter))
        binding.etDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih tanggal")
                .build()

            datePicker.show(parentFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = sdf.format(Date(selection))
                binding.etDate.setText(date)

                val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val dateFormatter = LocalDate.parse(date, inputFormatter)
                courseDate = dateFormatter.format(outputFormatter)
                binding.llPresence.visibility = View.GONE
            }
        }

        binding.btnCheck.setOnClickListener {
            loadData(courseId, classId, courseDate)
        }
    }

    private fun loadData(courseId: String, classId: String, courseDate: String) {
        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getTeacherCourseAttendance(courseId, classId, courseDate)
            attendanceList = response.data.toMutableList()

            if (attendanceList.isEmpty()) {
                binding.llPresence.visibility = View.GONE
            } else {
                binding.llPresence.visibility = View.VISIBLE
            }

            val adapter = PresenceAdapter(attendanceList)
            binding.rvPresence.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPresence.adapter = adapter

            binding.btnSubmit.setOnClickListener {
                val invalid = attendanceList.any { it.status.isNullOrEmpty() }
                if (invalid) {
                    Toast.makeText(requireContext(), "Masih ada siswa yang belum dipilih", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        attendanceList.forEach { data ->
                            val attendanceId = data.attendanceId ?: "null"
                            apiService.updateTeacherCourseAttendance(
                                attendanceId = attendanceId.toString(),
                                status = data.status!!,
                                nisn = data.nisn,
                                classId = classId,
                                courseId = courseId,
                                date = courseDate
                            )
                        }

                        Toast.makeText(requireContext(), "Absensi berhasil disimpan", Toast.LENGTH_SHORT).show()
                        loadData(courseId, classId, courseDate)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Gagal menyimpan absensi", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}