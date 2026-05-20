package com.example.mbsarfachruddin.network

import com.example.mbsarfachruddin.model.remote.quran.ayah.AyahResponse
import com.example.mbsarfachruddin.model.remote.quran.ayah.SuratSebelumnya
import com.example.mbsarfachruddin.model.remote.quran.ayah.SuratSebelumnyaDeserializer
import com.example.mbsarfachruddin.model.remote.quran.ayah.SuratSelanjutnya
import com.example.mbsarfachruddin.model.remote.quran.ayah.SuratSelanjutnyaDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {
    @GET("surat/{number}")
    suspend fun getAyah(
        @Path("number") number: Int,
    ) : AyahResponse

    companion object{
        private const val BASE_URL = "https://equran.id/api/v2/"

        fun create() : QuranApiService {
            val gson = GsonBuilder()
                .registerTypeAdapter(SuratSelanjutnya::class.java, SuratSelanjutnyaDeserializer())
                .registerTypeAdapter(SuratSebelumnya::class.java, SuratSebelumnyaDeserializer())
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(QuranApiService::class.java)
        }
    }

}