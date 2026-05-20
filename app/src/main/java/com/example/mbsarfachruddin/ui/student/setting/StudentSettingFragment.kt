package com.example.mbsarfachruddin.ui.student.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentStudentSettingBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class StudentSettingFragment : Fragment(R.layout.fragment_student_setting) {

    private val binding: FragmentStudentSettingBinding by viewBinding(FragmentStudentSettingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Pengaturan"
            setDisplayHomeAsUpEnabled(false)
        }

        val tinyDB = TinyDB(requireContext())

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getStudent(tinyDB.getString("user_id"))
            binding.tvName.text = response.data.name
            binding.tvUsername.text = "@${tinyDB.getString("username")}"
        }

        binding.llSettingUsername.setOnClickListener {
            findNavController().navigate(R.id.action_studentSettingFragment_to_studentSettingUsernameFragment)
        }
        binding.llSettingPassword.setOnClickListener {
            findNavController().navigate(R.id.action_studentSettingFragment_to_studentSettingPasswordFragment)
        }
        binding.llSettingNotification.setOnClickListener {
            Toast.makeText(requireContext(), "Segera Hadir", Toast.LENGTH_SHORT).show()
        }
        binding.llSettingLocation.setOnClickListener {
            Toast.makeText(requireContext(), "Segera Hadir", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            tinyDB.remove("user_id")
            tinyDB.remove("user_role")
            tinyDB.remove("remember_me")
            val apiService = ApiService.create()
            viewLifecycleOwner.lifecycleScope.launch {
                apiService.deleteToken(tinyDB.getString("fcm_token"))
            }
            findNavController().navigate(R.id.action_studentSettingFragment_to_loginFragment)
        }
    }
}