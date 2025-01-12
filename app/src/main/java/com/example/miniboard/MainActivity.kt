package com.example.miniboard

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miniboard.databinding.ActivityMainBinding
import com.example.miniboard.viewmodels.MainViewModel
import com.example.miniboard.viewmodelsfactories.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(TextService::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val viewModel: MainViewModel by viewModels { MainViewModelFactory(retrofitService) }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // private val messages: List<TextItem>
        val adapter = TextItemAdapter()
        binding.textRecyclerView.adapter = adapter

        viewModel.responseLiveData.observe(this) { response ->
            if (response.isSuccessful) {
                response.body()?.let { texts ->
                    // Передаем данные в адаптер
                    adapter.submitList(texts)
                }
            } else {
                Log.e("MainActivity", "Error: ${response.errorBody()?.string()}")
            }
        }
    }
}