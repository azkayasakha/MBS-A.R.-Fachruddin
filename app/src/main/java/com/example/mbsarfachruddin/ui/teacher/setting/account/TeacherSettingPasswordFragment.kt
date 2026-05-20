package com.example.mbsarfachruddin.ui.teacher.setting.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentTeacherSettingPasswordBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class TeacherSettingPasswordFragment : Fragment(R.layout.fragment_teacher_setting_password) {

    private val binding: FragmentTeacherSettingPasswordBinding by viewBinding(FragmentTeacherSettingPasswordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Ubah Kata Sandi"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            val password = binding.edtPassword.text.toString()
            val newPassword = binding.edtNewPassword.text.toString()
            val newPassword2 = binding.edtNewPassword2.text.toString()

            if (newPassword == newPassword2) {
                val apiService = ApiService.create()
                viewLifecycleOwner.lifecycleScope.launch {
                    val response = apiService.updateLoginPassword(TinyDB(requireContext()).getString("username"), password, newPassword)
                    if (response.status) {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Konfirmasi password baru tidak sama", Toast.LENGTH_SHORT).show()
            }
        }
    }
}