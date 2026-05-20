package com.example.mbsarfachruddin.ui.student.home.presence

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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
import com.example.mbsarfachruddin.databinding.FragmentStudentPresenceLessonBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class StudentPresenceLessonFragment : Fragment(R.layout.fragment_student_presence_lesson) {

    private val binding: FragmentStudentPresenceLessonBinding by viewBinding(FragmentStudentPresenceLessonBinding::bind)
    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        val subject = arguments?.getString("subject")
        val subjectId = arguments?.getString("subject_id")

        val userID = TinyDB(requireContext()).getString("user_id")

        binding.tvSubject.text = subject

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getStudentCourseAttendance(userID, subjectId!!)

            if (response.data.isEmpty()) {
                binding.llEmpty.visibility = View.VISIBLE
            } else {
                binding.llEmpty.visibility = View.GONE
            }

            val adapter = PresenceLessonAdapter(response.data)
            binding.rvPresence.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPresence.adapter = adapter
        }
    }

}