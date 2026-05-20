package com.example.mbsarfachruddin.ui.musyrif.home.presence

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.FragmentMusyrifPresenceBinding
import dev.androidbroadcast.vbpd.viewBinding

class MusyrifPresenceFragment : Fragment(R.layout.fragment_musyrif_presence) {

    private val binding: FragmentMusyrifPresenceBinding by viewBinding(FragmentMusyrifPresenceBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            title = "Pilih Presensi"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        binding.cvHalaqah.setOnClickListener {
            findNavController().navigate(R.id.action_musyrifPresenceFragment_to_musyrifPresenceTahfidzFragment)
        }

        binding.cvPrayer.setOnClickListener {
            findNavController().navigate(R.id.action_musyrifPresenceFragment_to_musyrifPresencePrayerFragment)
        }
    }
}