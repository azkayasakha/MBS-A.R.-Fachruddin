package com.example.mbsarfachruddin.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.SharedViewModel
import com.example.mbsarfachruddin.model.local.location.LocationData
import com.example.mbsarfachruddin.model.local.location.LocationResponse
import com.example.mbsarfachruddin.network.BlogApiService
import com.example.mbsarfachruddin.network.PrayerTimeApiService
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            fetchUserLocation(true)
        } else {
            fetchUserLocation(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isInternetAvailable(requireContext())) {
            Toast.makeText(requireContext(), "Tidak ada internet", Toast.LENGTH_SHORT).show()
            return
        }

        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    // =====================
    // 1️⃣ LOCATION
    // =====================
    @SuppressLint("MissingPermission")
    private fun fetchUserLocation(allGranted: Boolean) {
        val ctx = context ?: return

        var userLat = -6.32632087886941
        var userLng = 106.9393431576501

        if (allGranted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (!isAdded || location == null) return@addOnSuccessListener
                userLat = location.latitude
                userLng = location.longitude
            }
        } else {
            Toast.makeText(ctx, "Izin lokasi tidak diberikan, menggunakan lokasi default", Toast.LENGTH_SHORT).show()
        }

        sharedViewModel.userLat.value = userLat
        sharedViewModel.userLng.value = userLng

        val locations = loadLocationsFromAssets(ctx)
        val nearest = getNearestLocation(userLat, userLng, locations)
        nearest?.let {
            sharedViewModel.prayerTimeCity.value = it.lokasi
            fetchRemoteData(it.id)
        }
    }

    // =====================
    // 2️⃣ API CALL
    // =====================
    private fun fetchRemoteData(id: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val prayerApi = PrayerTimeApiService.create()
                val blogApi = BlogApiService.create()

                val prayerResponse = prayerApi.getPrayerTime(id)

                sharedViewModel.setPrayerTimes(
                    prayerResponse.data.jadwal.values.first()
                )

                val blogResponse = blogApi.getBlog("")
                sharedViewModel.setBlogData(blogResponse)

                navigateNext()

            } catch (e: Exception) {
                if (!isAdded) return@launch
                Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // =====================
    // 3️⃣ NAVIGATION
    // =====================
    private fun navigateNext() {
        if (!isAdded) return

        findNavController().navigate(
            R.id.action_splashFragment_to_loginFragment
        )
    }

    // =====================
    // UTIL
    // =====================
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected }
    }

    private fun showPermissionSettingsDialog() {
        if (!isAdded) return

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Izin dibutuhkan")
            .setMessage("Aktifkan izin di pengaturan aplikasi")
            .setPositiveButton("Pengaturan") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun openAppSettings() {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireContext().packageName, null)
            )
        )
    }

    private fun loadLocationsFromAssets(context: Context): List<LocationData> {
        val json = context.assets.open("location.json")
            .bufferedReader()
            .use { it.readText() }

        val response = Gson().fromJson(json, LocationResponse::class.java)
        return response.locations
    }

    private fun distanceKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) *
                cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }

    private fun getNearestLocation(userLat: Double, userLng: Double, locations: List<LocationData>): LocationData? {
        return locations.minByOrNull {
            distanceKm(userLat, userLng, it.latitude, it.longitude)
        }
    }
}
