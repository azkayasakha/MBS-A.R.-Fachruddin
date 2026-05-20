package com.example.mbsarfachruddin.ui.student.home.quran

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.FragmentStudentQuranBinding
import com.example.mbsarfachruddin.model.local.quran.surah.Data
import com.example.mbsarfachruddin.model.local.quran.surah.SurahResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.lang.reflect.Type

class StudentQuranFragment : Fragment(R.layout.fragment_student_quran) {

    private val binding: FragmentStudentQuranBinding by viewBinding(FragmentStudentQuranBinding::bind)
    private var fullSurahList: List<Data> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Al-Qur'an"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        with(binding) {
            rvSurah.layoutManager = LinearLayoutManager(requireContext())
            val surahAdapter = QuranSurahAdapter(emptyList())
            rvSurah.adapter = surahAdapter

            surahAdapter.itemClickListener = { data ->
                val bundle = Bundle().apply {
                    putInt("surahNumber", data.nomor)
                }
                findNavController().navigate(R.id.action_studentQuranFragment_to_studentQuranDetailFragment, bundle)
            }

            lifecycleScope.launch {
                try {
                    val surahList = loadSurahDataFromJson(requireContext())
                    fullSurahList = surahList
                    surahAdapter.updateData(fullSurahList)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            edtSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val input = s.toString()
                    val filteredList = fullSurahList.filter {
                        it.namaLatin.contains(input, ignoreCase = true) || it.nama.contains(input, ignoreCase = true)
                    }
                    surahAdapter.updateData(filteredList)
                }

            })
        }

    }

    private fun loadSurahDataFromJson(context: Context): List<Data> {
        val assetManager = context.assets
        val inputStream = assetManager.open("surah.json")
        val reader = InputStreamReader(inputStream)
        val gson = Gson()

        // Menggunakan tipe List<Data> untuk parse JSON
        val listType: Type = object : TypeToken<SurahResponse>() {}.type
        val surahResponse: SurahResponse = gson.fromJson(reader, listType)

        return surahResponse.data // Mengambil list data surah
    }

}