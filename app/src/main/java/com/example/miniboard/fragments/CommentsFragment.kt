package com.example.miniboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miniboard.R
import com.example.miniboard.databinding.FragmentCommentsBinding

class CommentsFragment : Fragment() {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)

        val messageId = arguments?.let {
            CommentsFragmentArgs.fromBundle(it).threadId
        }

        // Загрузите данные для треда, связанного с messageId
        // Пример: viewModel.loadThread(messageId)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
