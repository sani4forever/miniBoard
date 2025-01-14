package com.example.miniboard.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniboard.retrofit.TextItem
import com.example.miniboard.retrofit.TextService
import kotlinx.coroutines.launch

class CommentsViewModel(private val textService: TextService) : ViewModel() {

    private val _commentsLiveData = MutableLiveData<List<TextItem>>()
    val commentsLiveData: LiveData<List<TextItem>> get() = _commentsLiveData

    fun loadThread(threadId: Int) {
        viewModelScope.launch {
            try {
                val response = textService.getTextsById(threadId)
                if (response.isSuccessful) {
                    response.body()?.let { thread ->
                        val flatComments = flattenComments(thread)
                        _commentsLiveData.postValue(flatComments)
                    } ?: _commentsLiveData.postValue(emptyList())
                } else {
                    _commentsLiveData.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("Error", "Failed to load thread", e)
                _commentsLiveData.postValue(emptyList())
            }
        }
    }

    private fun flattenComments(item: TextItem): List<TextItem> {
        val result = mutableListOf<TextItem>()
        result.add(item)
        for (comment in item.comments) {
            result.addAll(flattenComments(comment))
        }
        return result
    }
}
