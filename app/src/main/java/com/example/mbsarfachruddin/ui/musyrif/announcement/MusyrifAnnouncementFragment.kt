package com.example.mbsarfachruddin.ui.musyrif.announcement

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.FragmentMusyrifAnnouncementBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class MusyrifAnnouncementFragment : Fragment(R.layout.fragment_musyrif_announcement) {

    private val binding: FragmentMusyrifAnnouncementBinding by viewBinding(FragmentMusyrifAnnouncementBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply {
            title = "Pengumuman"
            setDisplayHomeAsUpEnabled(false)
        }

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getAnnouncement("musyrif")

            if (response.data.isEmpty()) {
                binding.llEmpty.visibility = View.VISIBLE
            } else {
                binding.llEmpty.visibility = View.GONE
            }

            val adapter = AnnouncementAdapter(response.data)
            binding.rvAnnouncement.layoutManager = LinearLayoutManager(requireContext())
            binding.rvAnnouncement.adapter = adapter
        }
    }
}