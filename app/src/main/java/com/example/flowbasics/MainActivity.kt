package com.example.flowbasics

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flowbasics.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        simpleFlow()
        subscribeToObservables()

        binding.btnLiveData.setOnClickListener {
            mainViewModel.triggerLiveData()
        }

        binding.btnStateFlow.setOnClickListener {
            mainViewModel.triggerStateFlow()
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it
                }
            }
        }

        binding.btnSharedFlow.setOnClickListener {
            mainViewModel.triggerSharedFlow()
        }
    }

    private fun subscribeToObservables() {
            mainViewModel.liveData.observe(this){
                binding.tvLiveData.text = it
            }

        lifecycleScope.launchWhenStarted {
            mainViewModel.stateFlow.collectLatest {
                binding.tvStateFlow.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.sharedFlow.collectLatest {
                Snackbar.make(binding.root,it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    private fun simpleFlow() {
        val flow = flow<String> {
            for (i in 1..10) {
                emit("ABC")
                delay(1000L)
            }
        }

        GlobalScope.launch {
            flow.buffer().collect {
                println("Flow $it")
                delay(2000L)
            }
        }
    }

}
