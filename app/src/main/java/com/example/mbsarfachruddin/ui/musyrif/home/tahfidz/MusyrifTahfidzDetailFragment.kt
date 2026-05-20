package com.example.mbsarfachruddin.ui.musyrif.home.tahfidz

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.FragmentMusyrifTahfidzDetailBinding
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class MusyrifTahfidzDetailFragment : Fragment(R.layout.fragment_musyrif_tahfidz_detail) {

    private val binding: FragmentMusyrifTahfidzDetailBinding by viewBinding(FragmentMusyrifTahfidzDetailBinding::bind)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Tambah Tahfidz"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val studentId = arguments?.getString("nisn") ?: ""
        val halaqahId = arguments?.getInt("halaqahId") ?: 0

        var dataJuz = 1
        var dataPage = 1
        var dataPageSection = 0.0
        var dataType = "Ziyadah"

        val juzList = (1..30).toList() // List juz 1-30
        val pageList: List<List<Int>> = listOf(
            (1..21).toList(),    // Juz 1
            (22..41).toList(),   // Juz 2
            (42..61).toList(),   // Juz 3
            (62..81).toList(),   // Juz 4
            (82..101).toList(),  // Juz 5
            (102..121).toList(), // Juz 6
            (122..141).toList(), // Juz 7
            (142..161).toList(), // Juz 8
            (162..181).toList(), // Juz 9
            (182..201).toList(), // Juz 10
            (202..221).toList(), // Juz 11
            (222..241).toList(), // Juz 12
            (242..261).toList(), // Juz 13
            (262..281).toList(), // Juz 14
            (282..301).toList(), // Juz 15
            (302..321).toList(), // Juz 16
            (322..341).toList(), // Juz 17
            (342..361).toList(), // Juz 18
            (362..381).toList(), // Juz 19
            (382..401).toList(), // Juz 20
            (402..421).toList(), // Juz 21
            (422..441).toList(), // Juz 22
            (442..461).toList(), // Juz 23
            (462..481).toList(), // Juz 24
            (482..501).toList(), // Juz 25
            (502..521).toList(), // Juz 26
            (522..541).toList(), // Juz 27
            (542..561).toList(), // Juz 28
            (562..581).toList(), // Juz 29
            (582..604).toList()  // Juz 30
        ) // List halaman per juz

        val adapterJuz = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, juzList)
        binding.actvQuranJuz.setAdapter(adapterJuz)
        binding.actvQuranJuz.setOnItemClickListener { _, _, position, _ ->
            dataJuz = juzList[position]

            val adapterPage = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, pageList[juzList[position] - 1])
            binding.actvQuranPage.setAdapter(adapterPage)
            binding.actvQuranPage.setOnItemClickListener { _, _, position, _ ->
                dataPage = pageList[dataJuz - 1][position]
            }
        }

        val pageOpstions = listOf("1 Hal", "3/4 Hal", "1/2 Hal", "1/4 Hal")
        val adapterPageOption = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, pageOpstions)
        binding.actvQuranPageOption.setAdapter(adapterPageOption)
        binding.actvQuranPageOption.setOnItemClickListener { parent, view, position, id ->
            val selectedOption = parent.getItemAtPosition(position) as String
            dataPageSection = when (selectedOption) {
                "1 Hal" -> 1.0
                "3/4 Hal" -> 0.75
                "1/2 Hal" -> 0.5
                "1/4 Hal" -> 0.25
                else -> 0.0
            }
        }

        val tahfidzTypes = listOf("Ziyadah", "Murajaah")
        val adapterTahfidz = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, tahfidzTypes)
        binding.actvType.setAdapter(adapterTahfidz)
        binding.actvType.setOnItemClickListener { parent, view, position, id ->
            val selectedType = parent.getItemAtPosition(position) as String
            dataType = selectedType
        }

        binding.btnSave.setOnClickListener {
            val apiService = ApiService.create()
            viewLifecycleOwner.lifecycleScope.launch {

                val addTahfidzResponse = apiService.createMusyrifTahfidz(
                    nisn = studentId,
                    halaqahId = halaqahId,
                    quranJuz = dataJuz,
                    quranPage = dataPage,
                    quranPageSection = dataPageSection,
                    type = dataType
                )

                Toast.makeText(requireContext(), addTahfidzResponse.message, Toast.LENGTH_SHORT).show()

                if (addTahfidzResponse.status) {
                    loadTahfidz(studentId)
                    // Clear input fields after successful addition
                    binding.actvQuranJuz.text.clear()
                    binding.actvQuranPage.text.clear()
                    binding.actvQuranPageOption.text.clear()
                    binding.actvType.text.clear()
                }
            }
        }

        loadTahfidz(studentId)
    }

    private fun loadTahfidz(nisn: String) {
        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getMusyrifTahfidz(nisn)

            if (response.status) {
                if (response.data.isEmpty()) {
                    binding.llEmpty.visibility = View.VISIBLE
                } else {
                    binding.llEmpty.visibility = View.GONE
                }

                val adapter = TahfidzAdapter(response.data)
                binding.rvStudentTahfidz.layoutManager = LinearLayoutManager(requireContext())
                binding.rvStudentTahfidz.adapter = adapter
                adapter.itemClickListener = { data ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        val deleteTahfidz = apiService.deleteMusyrifTahfidz(data.id)
                        Toast.makeText(requireContext(), deleteTahfidz.message, Toast.LENGTH_SHORT).show()

                        if (deleteTahfidz.status) {
                            loadTahfidz(nisn)
                        }
                    }
                }
            }
        }
    }
}