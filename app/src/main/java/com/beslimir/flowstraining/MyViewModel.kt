package com.beslimir.flowstraining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class MyViewModel: ViewModel() {

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

}