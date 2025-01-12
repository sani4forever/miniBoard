package com.example.miniboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.miniboard.TextService
import com.example.miniboard.Texts
import retrofit2.Response

class MainViewModel(private val retrofitService: TextService) : ViewModel() {
    val responseLiveData: LiveData<Response<Texts>> = liveData {
        val response = retrofitService.getTexts()
        emit(response)
    }
}
