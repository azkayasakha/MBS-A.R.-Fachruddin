package com.example.mbsarfachruddin.ui.musyrif.home.tahfidz

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentMusyrifTahfidzBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class MusyrifTahfidzFragment : Fragment(R.layout.fragment_musyrif_tahfidz) {

    private val binding: FragmentMusyrifTahfidzBinding by viewBinding(FragmentMusyrifTahfidzBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Kelompok Halaqah"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val userId = TinyDB(requireContext()).getString("user_id")

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val responseHalaqah = apiService.getMusyrifHalaqah()
            val listHalaqah = responseHalaqah.data

            val halaqahNames = listHalaqah.map { it.name }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                halaqahNames
            )

            binding.actvHalaqah.setAdapter(adapter)

            // 🔹 SET DEFAULT HALAQAH BERDASARKAN userId
            val defaultHalaqah = listHalaqah.firstOrNull {
                it.musyrifId == userId
            }

            defaultHalaqah?.let {
                binding.actvHalaqah.setText(it.name, false) // false supaya tidak trigger dropdown
                setHalaqah(it.halaqahId)
            }

            binding.actvHalaqah.setOnItemClickListener { _, _, position, _ ->
                val selectedHalaqah = listHalaqah[position]
                setHalaqah(selectedHalaqah.halaqahId)
            }
        }
    }

    private fun setHalaqah(halaqahId: Int) {
        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getMusyrifHalaqahMemberByHalaqah(halaqahId)

            val adapter = StudentAdapter(response.data)
            binding.rvStudent.layoutManager = LinearLayoutManager(requireContext())
            binding.rvStudent.adapter = adapter
            adapter.itemClickListener = { data ->
                val bundle = Bundle().apply {
                    putString("nisn", data.nisn)
                    putInt("halaqahId", halaqahId)
                }
                findNavController().navigate(R.id.action_musyrifTahfidzFragment_to_musyrifTahfidzDetailFragment, bundle)
            }
        }
    }
}