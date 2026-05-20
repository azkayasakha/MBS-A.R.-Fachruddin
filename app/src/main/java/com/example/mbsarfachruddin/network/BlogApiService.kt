package com.example.mbsarfachruddin.network

import com.example.mbsarfachruddin.model.remote.blog.BlogResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogApiService {

    @GET("posts")
    suspend fun getBlog(
        @Query("_embed") embed: String,
        //@Query("per_page") perPage: Int,
    ) : BlogResponse

    companion object {
        private const val BASE_URL = "https://mbsarfachruddin.com/wp-json/wp/v2/"

        fun create() : BlogApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BlogApiService::class.java)
        }
    }
}