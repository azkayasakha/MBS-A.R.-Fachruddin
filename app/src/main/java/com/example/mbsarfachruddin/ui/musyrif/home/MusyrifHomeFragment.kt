package com.example.mbsarfachruddin.ui.musyrif.home

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.SharedViewModel
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentMusyrifHomeBinding
import com.example.mbsarfachruddin.model.remote.blog.BlogResponseItem
import com.example.mbsarfachruddin.network.ApiService
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MusyrifHomeFragment : Fragment(R.layout.fragment_musyrif_home) {

    private val binding: FragmentMusyrifHomeBinding by viewBinding(FragmentMusyrifHomeBinding::bind)
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var doubleBackToExitPressedOnce = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prayerTime()

        val userID = TinyDB(requireContext()).getString("user_id")

        val apiService = ApiService.create()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = apiService.getMusyrif(userID)

            binding.tvMusyrifName.text = response.data.name
            binding.tvMusyrifCode.text = response.data.musyrifId
        }

        with (binding) {
            llFtrTahfidz.setOnClickListener {
                findNavController().navigate(R.id.action_musyrifHomeFragment_to_musyrifTahfidzFragment)
            }

            llFtrPresence.setOnClickListener {
                findNavController().navigate(R.id.action_musyrifHomeFragment_to_musyrifPresenceFragment)
            }

            llFtrWallet.setOnClickListener {
                findNavController().navigate(R.id.action_musyrifHomeFragment_to_musyrifWalletFragment)
            }

            llFtrQuran.setOnClickListener {
                findNavController().navigate(R.id.action_musyrifHomeFragment_to_musyrifQuranFragment)
            }

            llFtrPrayerTime.setOnClickListener {
                findNavController().navigate(R.id.action_musyrifHomeFragment_to_musyrifPrayerTimeFragment)
            }
        }

        sharedViewModel.blogData.observe(viewLifecycleOwner, Observer { data ->
            showBlog(data)
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (doubleBackToExitPressedOnce) {
                requireActivity().finish()
            } else {
                doubleBackToExitPressedOnce = true
                Toast.makeText(requireContext(), "Klik sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prayerTime() {
        sharedViewModel.prayerTimes.observe(viewLifecycleOwner, Observer { data ->
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val prayerTimes = listOf(
                LocalTime.parse(data.subuh, formatter),
                LocalTime.parse(data.dzuhur, formatter),
                LocalTime.parse(data.ashar, formatter),
                LocalTime.parse(data.maghrib, formatter),
                LocalTime.parse(data.isya, formatter)
            )

            // Variabel untuk menyimpan waktu shalat berikutnya
            var nextPrayerTime: LocalTime? = null
            var nextPrayer: String? = null
            var nextPrayerIndex = -1

            handler = Handler()

            // Runnable untuk memperbarui countdown setiap detik
            runnable = object : Runnable {
                override fun run() {
                    val currentTime = LocalTime.now() // Memperbarui waktu saat ini setiap detik

                    // Cari waktu shalat berikutnya berdasarkan waktu saat ini
                    nextPrayerTime = null
                    nextPrayerIndex = -1
                    nextPrayer = null

                    for (i in prayerTimes.indices) {
                        if (currentTime.isBefore(prayerTimes[i])) {
                            nextPrayerTime = prayerTimes[i]
                            nextPrayerIndex = i
                            break
                        }
                    }

                    if (nextPrayerTime == null) {
                        // Jika semua waktu shalat sudah lewat, kembali ke Subuh
                        nextPrayerTime = prayerTimes[0]
                        nextPrayerIndex = 0
                    }

                    // Tentukan nama shalat yang sesuai
                    nextPrayer = when (nextPrayerIndex) {
                        0 -> "Subuh"
                        1 -> "Dzuhur"
                        2 -> "Ashar"
                        3 -> "Maghrib"
                        4 -> "Isya"
                        else -> ""
                    }

                    // Set nama shalat berikutnya ke tvPrayer
                    binding.tvPrayer.text = nextPrayer

                    // Set waktu shalat berikutnya ke tvPrayerTime
                    binding.tvPrayerTime.text = "${nextPrayerTime?.format(formatter)} WIB"

                    // Mengonversi LocalTime ke LocalDateTime untuk menghitung sisa waktu
                    val currentDateTime = LocalDateTime.now()
                    var adjustedNextPrayerTime = currentDateTime.withHour(nextPrayerTime!!.hour)
                        .withMinute(nextPrayerTime!!.minute)
                        .withSecond(0)
                        .withNano(0)

                    // Jika waktu shalat berikutnya sudah lewat, tambahkan satu hari
                    if (adjustedNextPrayerTime.isBefore(currentDateTime)) {
                        adjustedNextPrayerTime = adjustedNextPrayerTime.plusDays(1)
                    }

                    // Hitung sisa waktu mundur
                    val remainingTime = Duration.between(currentDateTime, adjustedNextPrayerTime)
                    val hoursLeft = remainingTime.toHours()
                    val minutesLeft = remainingTime.toMinutes() % 60
                    val secondsLeft = remainingTime.seconds % 60

                    // Set waktu hitungan mundur ke tvPrayerTimeCountdown
                    binding.tvPrayerTimeCountdown.text = "-${String.format("%02d:%02d:%02d", hoursLeft, minutesLeft, secondsLeft)}"

                    // Post the runnable again to update every second
                    handler.postDelayed(this, 1000)
                }
            }
            // Mulai countdown
            handler.post(runnable)
        })
    }

    private fun showBlog(list: List<BlogResponseItem>) {
        with (binding) {
            rvBlog.visibility = View.GONE

            // Setelah data diterima
            Handler(Looper.getMainLooper()).postDelayed({
                val blogListAdapter = BlogAdapter(list)
                rvBlog.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rvBlog.adapter = blogListAdapter
                rvBlog.visibility = View.VISIBLE
                blogListAdapter.itemClickListener = { data ->
                    val builder = CustomTabsIntent.Builder()
                    builder.setToolbarColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorPrimary
                        )
                    )
                    builder.setShowTitle(true)

                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(requireContext(), Uri.parse(data.link))
                }
            }, 1500) // delay untuk simulasi loading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }
}