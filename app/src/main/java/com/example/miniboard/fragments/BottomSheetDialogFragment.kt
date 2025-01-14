package com.example.miniboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.miniboard.databinding.FragmentBottomSheetDialogBinding
import com.example.miniboard.retrofit.RetrofitInstance
import com.example.miniboard.retrofit.TextItem
import com.example.miniboard.retrofit.TextService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetDialogBinding.inflate(inflater, container, false)

        val retrofitInstance = RetrofitInstance.getRetrofitInstance()
        val textService = retrofitInstance.create(TextService::class.java)

        val messageId = arguments?.let {
            BottomSheetDialogFragmentArgs.fromBundle(it).messageId
        }
        Log.d("abobas", "sent ${messageId}")

        binding.createThreadButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().ifEmpty { "Anonymous" }
            val text = binding.threadTextEditText.text.toString()

            if (messageId != 0) {
                createNewThread(
                    textService,
                    TextItem(
                        id = 0,
                        username = username,
                        text = text,
                        parentId = messageId!!,
                        createdAt = 0f,
                        commentDepth = 0,
                        comments = emptyList()
                    )
                )
                Log.d("abobas", "sent ${messageId}")

            } else if (text.isNotBlank()) {
                createNewThread(
                    textService,
                    TextItem(
                        id = 0,
                        username = username,
                        text = text,
                        parentId = -1,
                        createdAt = 0f,
                        commentDepth = 0,
                        comments = emptyList()
                    )
                )
                Log.d(
                    "aboba",
                    "sent ${
                        TextItem(
                            id = 0,
                            username = username,
                            text = text,
                            parentId = -1,
                            createdAt = 0f,
                            commentDepth = 0,
                            comments = emptyList()
                        )
                    }"
                )
            } else {
                Toast.makeText(requireContext(), "Введите текст треда", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    fun createNewThread(textService: TextService, newText: TextItem) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = textService.createText(newText)
                if (response.isSuccessful) {
                    println("Тред успешно создан")
                } else {
                    // Ошибка
                    println("Ошибка при создании треда: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Ошибка при выполнении запроса: ${e.message}")
            }
        }
    }
}
