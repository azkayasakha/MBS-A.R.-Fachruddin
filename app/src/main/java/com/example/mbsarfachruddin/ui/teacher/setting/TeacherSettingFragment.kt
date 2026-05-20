package com.example.mbsarfachruddin.ui.teacher.setting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentTeacherSettingBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class TeacherSettingFragment : Fragment(R.layout.fragment_teacher_setting) {

    private val binding: FragmentTeacherSettingBinding by viewBinding(FragmentTeacherSettingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Pengaturan"
            setDisplayHomeAsUpEnabled(false)
        }

        val tinyDB = TinyDB(requireContext())

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getTeacher(tinyDB.getString("user_id"))
            binding.tvName.text = response.data.name
            binding.tvUsername.text = "@${tinyDB.getString("username")}"
        }

        with(binding) {
            llSettingUsername.setOnClickListener {
                findNavController().navigate(R.id.action_teacherSettingFragment_to_teacherSettingUsernameFragment)
            }
            llSettingPassword.setOnClickListener {
                findNavController().navigate(R.id.action_teacherSettingFragment_to_teacherSettingPasswordFragment)
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
            findNavController().navigate(R.id.action_teacherSettingFragment_to_loginFragment)
        }
    }
}