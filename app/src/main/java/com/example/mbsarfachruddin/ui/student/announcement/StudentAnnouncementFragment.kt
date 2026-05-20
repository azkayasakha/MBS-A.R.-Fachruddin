package com.example.mbsarfachruddin.ui.student.announcement

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentStudentAnnouncementBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class StudentAnnouncementFragment : Fragment(R.layout.fragment_student_announcement) {

    private val binding: FragmentStudentAnnouncementBinding by viewBinding(FragmentStudentAnnouncementBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply {
            title = "Pengumuman"
            setDisplayHomeAsUpEnabled(false)
        }

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getAnnouncement("Student")

            if (response.data.isEmpty()) {
                binding.llEmpty.visibility = View.VISIBLE
            } else {
                binding.llEmpty.visibility = View.GONE
            }

            binding.rvAnnouncement.layoutManager = LinearLayoutManager(requireContext())
            binding.rvAnnouncement.adapter = AnnouncementAdapter(response.data)
        }
    }
}