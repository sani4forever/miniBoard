package com.example.miniboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.miniboard.databinding.FragmentMainBoardBinding
import com.example.miniboard.recyclerview.TextItemAdapter
import com.example.miniboard.retrofit.RetrofitInstance
import com.example.miniboard.retrofit.TextService
import com.example.miniboard.viewmodels.MainViewModel
import com.example.miniboard.viewmodelsfactories.MainViewModelFactory


class MainBoardFragment : Fragment() {
    private var _binding: FragmentMainBoardBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBoardBinding.inflate(inflater, container, false)
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(TextService::class.java)
        val viewModel: MainViewModel by viewModels { MainViewModelFactory(retrofitService) }

        val adapter = this.context?.let {
            TextItemAdapter(it) { messageId ->
                val action = MainBoardFragmentDirections.actionMainBoardFragmentToCommentsFragment(
                    messageId.toInt()
                )
                findNavController().navigate(action)
            }
        }
        binding.textRecyclerView.adapter = adapter


        viewModel.responseLiveData.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                response.body()?.let { texts ->
                    adapter?.submitList(texts)
                }
            } else {
                Log.e("MainActivity", "Error: ${response.errorBody()?.string()}")
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainBoardFragment().apply {}
    }
}