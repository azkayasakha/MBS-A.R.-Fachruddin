package com.example.mbsarfachruddin.ui.student.home.presence

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.SharedViewModel
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentStudentPresenceBinding
import com.example.mbsarfachruddin.network.ApiService
import com.google.android.material.chip.Chip
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class StudentPresenceFragment : Fragment(R.layout.fragment_student_presence) {

    private val binding: FragmentStudentPresenceBinding by viewBinding(FragmentStudentPresenceBinding::bind)
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Kehadiran"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Tentukan chip default sesuai hari ini
        val defaultChipId = when (dayOfWeek) {
            Calendar.SUNDAY -> R.id.chipAhad
            Calendar.MONDAY -> R.id.chipSenin
            Calendar.TUESDAY -> R.id.chipSelasa
            Calendar.WEDNESDAY -> R.id.chipRabu
            Calendar.THURSDAY -> R.id.chipKamis
            Calendar.FRIDAY -> R.id.chipJumat
            Calendar.SATURDAY -> R.id.chipSabtu
            else -> View.NO_ID
        }

        // Fungsi ambil tanggal sesuai hari dalam minggu ini
        fun getDateForDayOfWeek(targetDay: Int): String {
            val cal = Calendar.getInstance()
            val currentDay = cal.get(Calendar.DAY_OF_WEEK)

            // selisih dari hari sekarang ke target
            val diff = targetDay - currentDay
            cal.add(Calendar.DAY_OF_MONTH, diff)

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.format(cal.time)
        }

        if (defaultChipId != View.NO_ID) {
            binding.chipGroupDay.check(defaultChipId)

            val defaultChip = binding.chipGroupDay.findViewById<Chip>(defaultChipId)
            val date = getDateForDayOfWeek(dayOfWeek)
            schedule(defaultChip.text.toString().lowercase(), date)
        }

        // Listener untuk chip lain yang diklik
        binding.chipGroupDay.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val selectedChipId = checkedIds[0]
                val selectedChip = group.findViewById<Chip>(selectedChipId)

                // Tentukan tanggal sesuai chip
                val targetDay = when (selectedChipId) {
                    R.id.chipAhad -> Calendar.SUNDAY
                    R.id.chipSenin -> Calendar.MONDAY
                    R.id.chipSelasa -> Calendar.TUESDAY
                    R.id.chipRabu -> Calendar.WEDNESDAY
                    R.id.chipKamis -> Calendar.THURSDAY
                    R.id.chipJumat -> Calendar.FRIDAY
                    R.id.chipSabtu -> Calendar.SATURDAY
                    else -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                }

                val date = getDateForDayOfWeek(targetDay)
                schedule(selectedChip.text.toString().lowercase(), date)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun schedule(day: String, date: String) {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )
        val dateFormatter = LocalDate.parse(date, inputFormatter)
        val resultFormatter = dateFormatter.format(outputFormatter)
        binding.tvDate.text = resultFormatter

        val userID = TinyDB(requireContext()).getString("user_id")
        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val responseClassAttendance = apiService.getStudentClassAttendance(userID, date)
            val responseTahfidzAttendance = apiService.getStudentTahfidzAttendanceDetail(userID, date)
            val responsePrayerAttendance = apiService.getStudentPrayerAttendanceDetail(userID, date)

            if (responseClassAttendance.data.isEmpty()) {
                binding.llEmpty.visibility = View.VISIBLE
            } else {
                binding.llEmpty.visibility = View.GONE
            }

            val adapterClassAttendance = ScheduleAdapter(responseClassAttendance.data)
            binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
            binding.rvSchedule.adapter = adapterClassAttendance
            adapterClassAttendance.itemClickListener = { data ->
                val bundle = Bundle().apply {
                    putString("subject_id", data.courseId)
                    putString("subject", data.subject)
                }
                findNavController().navigate(R.id.action_studentPresenceFragment_to_studentPresenceLessonFragment, bundle)
            }

            with(responseTahfidzAttendance.data) {
                binding.tvTahfidzMorning.text = "• Pagi: ${pagi}"
                binding.tvTahfidzEvening.text = "• Sore: ${sore}"
            }

            with(responsePrayerAttendance.data) {
                binding.tvShalatShubuh.text = "• Shubuh: ${subuh}"
                binding.tvShalatDzuhur.text = "• Dzuhur: ${dzuhur}"
                binding.tvShalatAshar.text = "• Ashar: ${ashar}"
                binding.tvShalatMaghrib.text = "• Maghrib: ${maghrib}"
                binding.tvShalatIsya.text = "• Isya: ${isya}"
            }

            binding.llPresenceTahfidz.setOnClickListener {
                findNavController().navigate(R.id.action_studentPresenceFragment_to_studentPresenceTahfidzFragment)
            }
            binding.llPresencePrayer.setOnClickListener {
                findNavController().navigate(R.id.action_studentPresenceFragment_to_studentPresencePrayerFragment)
            }
        }
    }
}