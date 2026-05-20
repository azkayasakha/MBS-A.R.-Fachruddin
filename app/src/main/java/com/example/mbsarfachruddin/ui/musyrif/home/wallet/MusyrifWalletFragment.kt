package com.example.mbsarfachruddin.ui.musyrif.home.wallet

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentMusyrifWalletBinding
import com.example.mbsarfachruddin.network.ApiService
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

class MusyrifWalletFragment : Fragment(R.layout.fragment_musyrif_wallet) {

    private val binding: FragmentMusyrifWalletBinding by viewBinding(FragmentMusyrifWalletBinding::bind)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Top Up E-Money"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val userId = TinyDB(requireContext()).getString("user_id")

        var studentId = ""
        var studentName = ""

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val listStudent = apiService.getMusyrifHalaqahMemberByMusyrif(userId).data
            val studentNames = listStudent.map { it.name }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                studentNames
            )
            binding.actvStudent.setAdapter(adapter)
            binding.actvStudent.setOnItemClickListener { _, _, position, _ ->
                val selectedStudent = listStudent[position]
                studentId = selectedStudent.nisn
                studentName = selectedStudent.name
            }
        }

        binding.btnReview.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val response = apiService.getMusyrifWallet(studentId)
                if (response.status) {
                    val dialog = BottomSheetDialog(requireContext())

                    val view = layoutInflater.inflate(R.layout.bottom_sheet_musyrif_wallet, null)
                    val tvName = view.findViewById<TextView>(R.id.tv_name)
                    val tvAmount = view.findViewById<TextView>(R.id.tv_amount)
                    val tvBalance = view.findViewById<TextView>(R.id.tv_balance)
                    val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
                    val btnSave = view.findViewById<Button>(R.id.btn_save)

                    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

                    val balance = formatter.format(response.data.balance)
                    val amount = formatter.format(binding.edtAmount.text.toString().toIntOrNull() ?: 0)

                    tvName.text = studentName
                    tvAmount.text = amount
                    tvBalance.text = balance

                    btnCancel.setOnClickListener {
                        dialog.dismiss()
                    }

                    btnSave.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            val update = apiService.updateMusyrifWallet(studentId, binding.edtAmount.text.toString().toIntOrNull() ?: 0)
                            if (update.status) {
                                Toast.makeText(requireContext(), "Saldo berhasil ditambah! Saldo: ${formatter.format(update.data.newBalance)}", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                        }
                    }

                    dialog.setContentView(view)
                    dialog.show()
                } else {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}