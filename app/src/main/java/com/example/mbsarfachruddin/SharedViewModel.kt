package com.example.mbsarfachruddin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mbsarfachruddin.model.remote.blog.BlogResponse
import com.example.mbsarfachruddin.model.remote.prayertime.JadwalHarian

class SharedViewModel : ViewModel() {
    var userLat = MutableLiveData<Double>()
    var userLng = MutableLiveData<Double>()

    var prayerTimeCity = MutableLiveData<String>()
    private val _prayerTimes = MutableLiveData<JadwalHarian>()
    val prayerTimes: LiveData<JadwalHarian> get() = _prayerTimes
    fun setPrayerTimes(prayerTimes: JadwalHarian) {
        _prayerTimes.value = prayerTimes
    }

    private val _blogData = MutableLiveData<BlogResponse>()
    val blogData: LiveData<BlogResponse> get() = _blogData
    fun setBlogData(blogResponse: BlogResponse) {
        _blogData.value = blogResponse
    }

    var nowQuranSurah = MutableLiveData<String>()
}