package com.example.mbsarfachruddin.ui.musyrif.home.prayertime

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.SharedViewModel
import com.example.mbsarfachruddin.databinding.FragmentMusyrifPrayerTimeBinding
import dev.androidbroadcast.vbpd.viewBinding
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class MusyrifPrayerTimeFragment : Fragment(R.layout.fragment_musyrif_prayer_time), SensorEventListener {

    private val binding: FragmentMusyrifPrayerTimeBinding by viewBinding(FragmentMusyrifPrayerTimeBinding::bind)
    private val sharedViewModel: SharedViewModel by activityViewModels()

    // --- Timer & Handler ---
    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null

    // --- Sensor & Compass (Smoothing) ---
    private lateinit var sensorManager: SensorManager
    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null
    private var currentDegree = 0f
    private var qiblaDegree = 0f
    private val ALPHA = 0.05f

    // Konstanta Lokasi Ka'bah
    private val KAABA_LAT = 21.42267763235978
    private val KAABA_LNG = 39.82619171536255

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Setup City Name
        binding.tvPrayerCity.text = sharedViewModel.prayerTimeCity.value?.toString()
            ?.lowercase()
            ?.split(" ")
            ?.joinToString(" ") { it.replaceFirstChar(Char::uppercase) }

        // Observer Data Jadwal Shalat & Countdown
        sharedViewModel.prayerTimes.observe(viewLifecycleOwner) { data ->
            with(binding) {
                tvPrayerSubuh.text = "${data.subuh} WIB"
                tvPrayerDhuhur.text = "${data.dzuhur} WIB"
                tvPrayerAshar.text = "${data.ashar} WIB"
                tvPrayerMaghrib.text = "${data.maghrib} WIB"
                tvPrayerIsya.text = "${data.isya} WIB"
            }

            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val prayerTimesList = listOf(
                LocalTime.parse(data.subuh, formatter),
                LocalTime.parse(data.dzuhur, formatter),
                LocalTime.parse(data.ashar, formatter),
                LocalTime.parse(data.maghrib, formatter),
                LocalTime.parse(data.isya, formatter)
            )
            startCountdown(prayerTimesList, formatter)
        }

        // --- AMBIL LOKASI DARI VIEWMODEL ---
        val userLat = sharedViewModel.userLat.value ?: -6.326318879462791 // Default jika null
        val userLng = sharedViewModel.userLng.value ?: 106.9393431576501 // Default jika null
        qiblaDegree = calculateQiblaDirection(userLat, userLng)
    }

    private fun setupToolbar() {
        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            title = "Jadwal Shalat"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startCountdown(prayerTimes: List<LocalTime>, formatter: DateTimeFormatter) {
        timerRunnable?.let { handler.removeCallbacks(it) }
        timerRunnable = object : Runnable {
            override fun run() {
                if (!isAdded || view == null) return

                val currentTime = LocalTime.now()
                var nextPrayerTime: LocalTime? = null
                var nextPrayerIndex = -1

                for (i in prayerTimes.indices) {
                    if (currentTime.isBefore(prayerTimes[i])) {
                        nextPrayerTime = prayerTimes[i]
                        nextPrayerIndex = i
                        break
                    }
                }

                if (nextPrayerTime == null) {
                    nextPrayerTime = prayerTimes[0]
                    nextPrayerIndex = 0
                }

                val nextPrayerName = when (nextPrayerIndex) {
                    0 -> "Subuh"
                    1 -> "Dzuhur"
                    2 -> "Ashar"
                    3 -> "Maghrib"
                    4 -> "Isya"
                    else -> ""
                }

                binding.tvPrayer.text = nextPrayerName
                binding.tvPrayerTime.text = "${nextPrayerTime?.format(formatter)} WIB"

                val currentDateTime = LocalDateTime.now()
                var targetDateTime = currentDateTime.withHour(nextPrayerTime!!.hour)
                    .withMinute(nextPrayerTime.minute)
                    .withSecond(0).withNano(0)

                if (targetDateTime.isBefore(currentDateTime)) targetDateTime = targetDateTime.plusDays(1)

                val remainingTime = Duration.between(currentDateTime, targetDateTime)
                binding.tvPrayerTimeCountdown.text = "-${String.format("%02d:%02d:%02d", remainingTime.toHours(), remainingTime.toMinutes() % 60, remainingTime.seconds % 60)}"

                handler.postDelayed(this, 1000)
            }
        }
        handler.post(timerRunnable!!)
    }

    private fun calculateQiblaDirection(lat: Double, lng: Double): Float {
        val userLatRad = Math.toRadians(lat)
        val userLngRad = Math.toRadians(lng)
        val kaabaLatRad = Math.toRadians(KAABA_LAT)
        val kaabaLngRad = Math.toRadians(KAABA_LNG)

        val y = sin(kaabaLngRad - userLngRad)
        val x = cos(userLatRad) * tan(kaabaLatRad) - sin(userLatRad) * cos(kaabaLngRad - userLngRad)
        val result = Math.toDegrees(atan2(y, x))
        return ((result + 360) % 360).toFloat()
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timerRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            gravity = lowPass(event.values.clone(), gravity)
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = lowPass(event.values.clone(), geomagnetic)
        }

        if (gravity != null && geomagnetic != null) {
            val r = FloatArray(9)
            val i = FloatArray(9)
            if (SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(r, orientation)
                val azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
                var targetDegree = (qiblaDegree - azimuth)

                if (targetDegree - currentDegree > 180) targetDegree -= 360
                else if (currentDegree - targetDegree > 180) targetDegree += 360

                val rotateAnimation = RotateAnimation(
                    currentDegree, targetDegree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                ).apply {
                    duration = 200
                    fillAfter = true
                }
                binding.ivQibla.startAnimation(rotateAnimation)
                currentDegree = targetDegree
            }
        }
    }

    private fun lowPass(input: FloatArray, output: FloatArray?): FloatArray {
        if (output == null) return input
        for (i in input.indices) {
            output[i] = output[i] + ALPHA * (input[i] - output[i])
        }
        return output
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}