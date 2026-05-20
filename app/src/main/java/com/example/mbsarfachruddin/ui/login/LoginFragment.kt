package com.example.mbsarfachruddin.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentLoginBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tinyDB = TinyDB(requireContext())

        if (tinyDB.getBoolean("remember_me")) {
            when (tinyDB.getString("user_role")) {
                "student" -> {findNavController().navigate(R.id.action_loginFragment_to_studentHomeFragment)}
                "musyrif" -> {findNavController().navigate(R.id.action_loginFragment_to_musyrifHomeFragment)}
                "teacher" -> {findNavController().navigate(R.id.action_loginFragment_to_teacherHomeFragment)}
            }
            return
        }

        with(binding) {
            tvForgotPassword.setOnClickListener {
                Toast.makeText(requireContext(), "Harap hubungi admin!", Toast.LENGTH_SHORT).show()
            }
            btnLogin.setOnClickListener {

                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                if (username.isEmpty() || password.isEmpty()) {
                    showToast("Username dan Password tidak boleh kosong!")
                } else {
                    val apiService = ApiService.create()
                    viewLifecycleOwner.lifecycleScope.launch {
                        val login = apiService.getLogin(username, password)
                        if (login.status) {
                            tinyDB.putBoolean("remember_me", cbRememberMe.isChecked)
                            apiService.deleteToken(tinyDB.getString("fcm_token"))
                            apiService.createToken(login.data!!.referenceId, tinyDB.getString("fcm_token"))
                            when (login.data.role) {
                                "student" -> {
                                    tinyDB.putString("username", username)
                                    tinyDB.putString("user_id", login.data.referenceId)
                                    tinyDB.putString("user_role", "student")
                                    findNavController().navigate(R.id.action_loginFragment_to_studentHomeFragment)
                                }
                                "musyrif" -> {
                                    tinyDB.putString("username", username)
                                    tinyDB.putString("user_id", login.data.referenceId)
                                    tinyDB.putString("user_role", "musyrif")
                                    findNavController().navigate(R.id.action_loginFragment_to_musyrifHomeFragment)
                                }
                                "teacher" -> {
                                    tinyDB.putString("username", username)
                                    tinyDB.putString("user_id", login.data.referenceId)
                                    tinyDB.putString("user_role", "teacher")
                                    findNavController().navigate(R.id.action_loginFragment_to_teacherHomeFragment)
                                }
                            }
                        } else {
                            showToast(login.message)
                        }
                    }
                }
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}