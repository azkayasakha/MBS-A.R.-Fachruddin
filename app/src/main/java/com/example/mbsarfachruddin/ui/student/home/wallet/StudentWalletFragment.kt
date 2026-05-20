package com.example.mbsarfachruddin.ui.student.home.wallet

import android.os.Bundle
import android.util.Log
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
import com.example.mbsarfachruddin.databinding.FragmentStudentWalletBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class StudentWalletFragment : Fragment(R.layout.fragment_student_wallet) {

    private val binding: FragmentStudentWalletBinding by viewBinding(FragmentStudentWalletBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Uang Saku"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val userID = TinyDB(requireContext()).getString("user_id")

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getStudentTransaction(userID)

            val rupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                .format(response.data.balance)
                .replace(",00", "")
            binding.tvBalance.text = rupiah

            if (response.data.history.isEmpty()) {
                binding.llEmpty.visibility = View.VISIBLE
            }

            val adapter = TransactionAdapter(response.data.history)
            binding.rvWallet.layoutManager = LinearLayoutManager(requireContext())
            binding.rvWallet.adapter = adapter
        }
    }
}