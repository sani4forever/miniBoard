package com.example.miniboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.miniboard.databinding.FragmentCommentsBinding
import com.example.miniboard.recyclerview.TextItemAdapter
import com.example.miniboard.retrofit.RetrofitInstance
import com.example.miniboard.retrofit.TextService
import com.example.miniboard.viewmodels.CommentsViewModel
import com.example.miniboard.viewmodelsfactories.CommentsViewModelFactory

class CommentsFragment : Fragment() {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(TextService::class.java)
        val viewModel: CommentsViewModel by viewModels { CommentsViewModelFactory(retrofitService) }

        val adapter = TextItemAdapter(
            requireContext(),
            clickListener = { _ ->
            },
            longClickListener = { messageId ->
                val action =
                    CommentsFragmentDirections.actionCommentsFragmentToBottomSheetDialogFragment2(
                        messageId.toInt()
                    )
                findNavController().navigate(action)
            }
        )
        binding.commentsRecyclerView.adapter = adapter

        val threadId = arguments?.let {
            CommentsFragmentArgs.fromBundle(it).threadId
        }

        threadId?.let { id ->
            viewModel.loadThread(id)
        }

        viewModel.commentsLiveData.observe(viewLifecycleOwner) { comments ->
            adapter.submitList(comments)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

