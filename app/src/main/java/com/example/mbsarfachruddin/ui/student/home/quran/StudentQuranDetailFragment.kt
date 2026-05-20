package com.example.mbsarfachruddin.ui.student.home.quran

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.SharedViewModel
import com.example.mbsarfachruddin.databinding.FragmentStudentQuranDetailBinding
import com.example.mbsarfachruddin.network.QuranApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class StudentQuranDetailFragment : Fragment(R.layout.fragment_student_quran_detail) {

    private val binding: FragmentStudentQuranDetailBinding by viewBinding(FragmentStudentQuranDetailBinding::bind)
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var mediaPlayer: MediaPlayer? = null
    private var isMurattalPlaying = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Al-Qur'an Detail"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val quranApi = QuranApiService.create()

        lifecycleScope.launch {
            val surahNumber = arguments?.getInt("surahNumber")
            val response = quranApi.getAyah(surahNumber!!)

            sharedViewModel.nowQuranSurah.value = response.data.nomor.toString()

            with(binding) {
                tvSurahNo.text = response.data.nomor.toString()
                tvSurahName.text = response.data.namaLatin
                tvSurahDesc.text = "${response.data.arti} - ${response.data.jumlahAyat} Ayat"
                tvSurahNameAr.text = response.data.nama

                imgbtnPlayFull.setOnClickListener {
                    val surahNumber = response.data.nomor
                    val ayahCount = response.data.jumlahAyat

                    if (isMurattalPlaying) {
                        pauseAudio()
                        imgbtnPlayFull.setImageResource(R.drawable.ic_music_play)
                    } else {
                        playFullSurah(surahNumber, ayahCount)
                        imgbtnPlayFull.setImageResource(R.drawable.ic_music_stop)
                    }
                }

                val ayahAdapter = QuranAyahAdapter(response.data.ayat, sharedViewModel)
                rvAyah.layoutManager = LinearLayoutManager(requireContext())
                rvAyah.adapter = ayahAdapter
            }
        }
    }

    private fun playFullSurah(surahNumber: Int, ayahCount: Int) {
        var url = ""
        val urlList = mutableListOf<String>()
        if (surahNumber != 1 && surahNumber != 9) {
            url = generateUrlForAyah(1, 1)
            urlList.add(url)
        }

        for (ayahNumber in 1..ayahCount) {
            url = generateUrlForAyah(surahNumber, ayahNumber)
            urlList.add(url)
        }

        // Mulai memutar ayah pertama
        playNextAyah(urlList, 0)
    }

    // Fungsi untuk melanjutkan ke ayah berikutnya setelah audio selesai diputar
    private fun playNextAyah(urlList: List<String>, currentIndex: Int) {
        if (currentIndex >= urlList.size) {
            // Semua audio telah diputar
            isMurattalPlaying = false
            binding.imgbtnPlayFull.setImageResource(R.drawable.ic_music_play)
            return
        }

        val url = urlList[currentIndex]
        playAudio(url, currentIndex, urlList)
    }

    // Fungsi untuk memutar audio
    private fun playAudio(url: String, currentIndex: Int, urlList: List<String>) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                start()  // Mulai pemutaran
                isMurattalPlaying = true
            }
            setOnCompletionListener {
                // Setelah audio selesai, lanjutkan ke audio berikutnya
                playNextAyah(urlList, currentIndex + 1)
            }
            setOnErrorListener { mp, what, extra ->
                Toast.makeText(context, "Error playing audio", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    // Fungsi untuk membuat URL berdasarkan nomor surah dan ayah dengan format yang benar
    private fun generateUrlForAyah(surahNumber: Int, ayahNumber: Int): String {
        // Menggunakan String.format untuk memastikan nomor surah dan ayah 3 digit
        val surah = String.format("%03d", surahNumber)   // Memastikan surah memiliki 3 digit
        val ayah = String.format("%03d", ayahNumber)     // Memastikan ayah memiliki 3 digit

        return "https://cdn.equran.id/audio-partial/Misyari-Rasyid-Al-Afasi/$surah$ayah.mp3"
    }

    // Fungsi untuk pause audio
    private fun pauseAudio() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isMurattalPlaying = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}