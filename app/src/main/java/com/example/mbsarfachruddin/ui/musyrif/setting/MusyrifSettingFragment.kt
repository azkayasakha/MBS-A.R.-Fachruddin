package com.example.mbsarfachruddin.ui.musyrif.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentMusyrifSettingBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class MusyrifSettingFragment : Fragment(R.layout.fragment_musyrif_setting) {

    private val binding: FragmentMusyrifSettingBinding by viewBinding(FragmentMusyrifSettingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Pengaturan"
            setDisplayHomeAsUpEnabled(false)
        }

        val tinyDB = TinyDB(requireContext())

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getMusyrif(tinyDB.getString("user_id"))
            binding.tvName.text = response.data.name
            binding.tvUsername.text = "@${tinyDB.getString("username")}"
        }

        with(binding) {
            llSettingUsername.setOnClickListener {
                findNavController().navigate(R.id.action_musyrifSettingFragment_to_musyrifSettingUsernameFragment)
            }
            llSettingPassword.setOnClickListener {
                findNavController().navigate(R.id.action_musyrifSettingFragment_to_musyrifSettingPasswordFragment)
            }
            llSettingNotification.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur segera hadir!", Toast.LENGTH_SHORT).show()
            }
            llSettingLocation.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur segera hadir!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogout.setOnClickListener {
            tinyDB.remove("user_id")
            tinyDB.remove("user_role")
            tinyDB.remove("remember_me")
            findNavController().navigate(R.id.action_musyrifSettingFragment_to_loginFragment)
        }
    }
}