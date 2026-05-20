package com.example.mbsarfachruddin.ui.teacher.setting.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentTeacherSettingUsernameBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class TeacherSettingUsernameFragment : Fragment(R.layout.fragment_teacher_setting_username) {

    private val binding: FragmentTeacherSettingUsernameBinding by viewBinding(FragmentTeacherSettingUsernameBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Ubah Nama Pengguna"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val tinyDB = TinyDB(requireContext())

        binding.btnSave.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (username == "admin" || username == "Admin") {
                    Toast.makeText(
                        requireContext(),
                        "Dilarang menggunakan username tersebut!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val apiService = ApiService.create()
                    viewLifecycleOwner.lifecycleScope.launch {
                        val response = apiService.updateLoginUsername(
                            tinyDB.getString("username"),
                            password,
                            username
                        )
                        if (response.status) {
                            tinyDB.putString("username", username)
                            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                                .show()
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}