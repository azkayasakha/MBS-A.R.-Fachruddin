package com.example.mbsarfachruddin.ui.musyrif.home.presence

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentMusyrifPresenceTahfidzBinding
import com.example.mbsarfachruddin.databinding.FragmentStudentPresenceTahfidzBinding
import com.example.mbsarfachruddin.model.remote.musyrif.halaqahattendance.Data
import com.example.mbsarfachruddin.network.ApiService
import com.google.android.material.datepicker.MaterialDatePicker
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class MusyrifPresenceTahfidzFragment : Fragment(R.layout.fragment_musyrif_presence_tahfidz) {

    private val binding: FragmentMusyrifPresenceTahfidzBinding by viewBinding(FragmentMusyrifPresenceTahfidzBinding::bind)
    private lateinit var attendanceList: MutableList<Data>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            title = "Presensi Halaqah"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        val musyrifId = TinyDB(requireContext()).getString("user_id")
        var halaqah = ""
        var halaqahDate  = LocalDate.now().toString()

        val halaqahTypes = listOf("Pagi", "Sore")
        val adapterHalaqah = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, halaqahTypes)
        binding.actvHalaqah.setAdapter(adapterHalaqah)
        binding.actvHalaqah.setOnItemClickListener { parent, view, position, id ->
            val selectedType = parent.getItemAtPosition(position) as String
            halaqah = selectedType
            binding.llPresence.visibility = View.GONE
        }

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
                halaqahDate = dateFormatter.format(outputFormatter)
                binding.llPresence.visibility = View.GONE
            }
        }

        binding.btnCheck.setOnClickListener {
            if (halaqah.isNotEmpty()) {
                loadData(musyrifId, halaqah, halaqahDate)
            } else {
                Toast.makeText(requireContext(), "Harap pilih waktu halaqah!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData(musyrifId: String, time: String, date: String) {
        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getMusyrifAttendanceHalaqah(musyrifId, time, date)

            attendanceList = response.data.toMutableList()

            if (attendanceList.isEmpty()) {
                binding.llPresence.visibility = View.GONE
            } else {
                binding.llPresence.visibility = View.VISIBLE
            }

            val adapter = HalaqahAdapter(attendanceList)
            binding.rvPresence.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPresence.adapter = adapter

            binding.btnSubmit.setOnClickListener {
                val invalid = attendanceList.any { it.status.isNullOrEmpty() }
                if (invalid) {
                    Toast.makeText(requireContext(), "Masih ada santri yang belum dipilih", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        attendanceList.forEach { data ->
                            val attendanceId = data.attendanceId ?: "null"
                            apiService.updateMusyrifAttendanceHalaqah(
                                attendanceId = attendanceId.toString(),
                                nisn = data.nisn,
                                time = time,
                                status = data.status!!,
                                date = date
                            )
                        }

                        Toast.makeText(requireContext(), "Absensi berhasil disimpan", Toast.LENGTH_SHORT).show()
                        loadData(musyrifId, time, date)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Gagal menyimpan absensi", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}