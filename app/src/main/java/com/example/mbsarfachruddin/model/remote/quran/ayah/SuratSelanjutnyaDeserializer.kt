package com.example.mbsarfachruddin.model.remote.quran.ayah

import com.google.gson.JsonElement
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import java.lang.reflect.Type

class SuratSelanjutnyaDeserializer : JsonDeserializer<SuratSelanjutnya?> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): SuratSelanjutnya? {
        return if (json?.isJsonObject == true) {
            val jsonObject = json.asJsonObject
            SuratSelanjutnya(
                jumlahAyat = jsonObject.get("jumlahAyat").asInt,
                nama = jsonObject.get("nama").asString,
                namaLatin = jsonObject.get("namaLatin").asString,
                nomor = jsonObject.get("nomor").asInt
            )
        } else {
            null
        }
    }
}