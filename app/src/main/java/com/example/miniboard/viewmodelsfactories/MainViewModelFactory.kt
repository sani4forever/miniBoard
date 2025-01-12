package com.example.miniboard.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniboard.TextService
import com.example.miniboard.viewmodels.MainViewModel

class MainViewModelFactory(private val retrofitService: TextService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(retrofitService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
