package com.example.flowbasics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _liveData = MutableLiveData("Hello World")
    val liveData : LiveData<String> = _liveData

        //Hard Flow
    private val _stateFlow = MutableStateFlow("Hello World")
    val stateFlow = _stateFlow.asStateFlow()

    //HardFlow - Used to send a one time event
    //automatically emit the value when activity is recreated
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun triggerLiveData() {
        _liveData.value = "LiveData"
    }

    fun triggerStateFlow(){
        _stateFlow.value = "StateFlow"
    }

    fun triggerFlow() : Flow<String> {
        return flow {
            repeat(5){
                emit("Item $it")
                delay(1000L)
            }
        }
    }

    //One time event
    fun triggerSharedFlow(){
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }



}