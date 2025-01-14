package com.example.miniboard.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniboard.retrofit.TextService
import com.example.miniboard.viewmodels.CommentsViewModel

class CommentsViewModelFactory(private val textService: TextService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            return CommentsViewModel(textService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}