package com.example.mbsarfachruddin.ui.student.home.presence

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentStudentPresencePrayerBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.util.Calendar

class StudentPresencePrayerFragment : Fragment(R.layout.fragment_student_presence_prayer) {

    private val binding: FragmentStudentPresencePrayerBinding by viewBinding(FragmentStudentPresencePrayerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Detail Kehadiran"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val userID = TinyDB(requireContext()).getString("user_id")

        val monthMap = mapOf(
            "Januari" to 1, "Februari" to 2, "Maret" to 3,
            "April" to 4, "Mei" to 5, "Juni" to 6,
            "Juli" to 7, "Agustus" to 8, "September" to 9,
            "Oktober" to 10, "November" to 11, "Desember" to 12
        )
        val months = monthMap.keys.toList()
        val adapterMonth = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            months
        )
        binding.actvMonth.setAdapter(adapterMonth)

        // Ambil bulan saat ini
        val currentMonthNumber = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentMonthName = monthMap.entries.first { it.value == currentMonthNumber }.key
        binding.actvMonth.setText(currentMonthName, false)

        loadAttendance(userID, currentMonthNumber)

        // Jika user memilih bulan lain
        binding.actvMonth.setOnItemClickListener { _, _, position, _ ->
            val selectedMonth = months[position]
            val monthValue = monthMap[selectedMonth] ?: return@setOnItemClickListener
            loadAttendance(userID, monthValue)
        }
    }

    private fun loadAttendance(userID: String, month: Int) {
        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getStudentPrayerAttendance(userID, month)

            if (response.data.isEmpty()) {
                binding.llEmpty.visibility = View.VISIBLE
            } else {
                binding.llEmpty.visibility = View.GONE
            }

            val adapter = PresencePrayerAdapter(response.data)
            binding.rvPresence.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPresence.adapter = adapter
        }
    }
}