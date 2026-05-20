package com.example.mbsarfachruddin.network

import com.example.mbsarfachruddin.model.remote.prayertime.PrayerTimeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PrayerTimeApiService {
    @GET("sholat/jadwal/{id}/today")
    suspend fun getPrayerTime(
        @Path("id") id: String,
    ) : PrayerTimeResponse

    companion object {
        private const val BASE_URL = "https://api.myquran.com/v3/"

        fun create() : PrayerTimeApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrayerTimeApiService::class.java)
        }
    }
}