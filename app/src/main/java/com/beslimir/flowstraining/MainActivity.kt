package com.beslimir.flowstraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MyViewModel>()
            val timeFlow = viewModel.countDownFlow.collectAsState(initial = 10)
            val timeStateFlow = viewModel.stateFlow.collectAsState()

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //cold flow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = timeFlow.value.toString())
                }

                //LiveData
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            viewModel.triggerLiveData()
                        }
                    ) {
                        Text(text = "Live Data Button")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = viewModel.liveData.value.toString()
                    )
                }

                //StateFlow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            viewModel.triggerStateFlow()
                        }
                    ) {
                        Text(text = "StateFlow Button")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = timeStateFlow.value
                    )
                }
            }
        }
    }
}