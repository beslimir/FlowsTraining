package com.beslimir.flowstraining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    //LiveData
    private val _liveData = MutableLiveData("Hello")
    val liveData: LiveData<String> = _liveData

    fun triggerLiveData() {
        _liveData.value = "Live Data"
    }

    //Cold flow
    val countDownFlow = flow<Int> {
        val start = 10
        var currValue = start
        emit(currValue)
        while (currValue > 0) {
            delay(1000L)
            currValue--
            emit(currValue)
        }
    }

    //StateFlow
    private val _stateFlow = MutableStateFlow("Hello")
    val stateFlow = _stateFlow.asStateFlow()

    fun triggerStateFlow() {
        _stateFlow.value = "StateFlow"
    }

    //Channels
    private val _channel = Channel<Int>(100)
    val channel =
        _channel.receiveAsFlow() //consumeAsFlow collects just once; receiveAsFlow supports multiple collectors

    fun triggerChannelData() {
        viewModelScope.launch {
            for (i in 10 downTo 0) {
                _channel.send(i)
                delay(500L)
            }
        }
    }

    //SharedFlow
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }

    private fun showToastAfterTimerEnds() {
        viewModelScope.launch {
            for (i in 15 downTo 0) {
                if (i == 0 || i == 10) {
                    _sharedFlow.emit("Counter is $i!")
                }
                delay(1000L)
            }
        }
    }

    init {
        showToastAfterTimerEnds()
    }

}