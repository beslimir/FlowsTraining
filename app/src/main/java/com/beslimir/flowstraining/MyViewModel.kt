package com.beslimir.flowstraining

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MyViewModel: ViewModel() {

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

}