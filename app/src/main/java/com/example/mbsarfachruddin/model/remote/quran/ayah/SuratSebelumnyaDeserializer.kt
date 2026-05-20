package com.example.mbsarfachruddin.model.remote.quran.ayah

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class SuratSebelumnyaDeserializer : JsonDeserializer<SuratSebelumnya?> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): SuratSebelumnya? {
        return if (json?.isJsonObject == true) {
            val jsonObject = json.asJsonObject
            SuratSebelumnya(
                nomor = jsonObject.get("nomor").asInt,
                nama = jsonObject.get("nama").asString,
                namaLatin = jsonObject.get("namaLatin").asString,
                jumlahAyat = jsonObject.get("jumlahAyat").asInt
            )
        } else {
            null
        }
    }
}