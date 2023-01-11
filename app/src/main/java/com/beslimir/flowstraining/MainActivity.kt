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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MyViewModel>()
            val timeFlow = viewModel.countDownFlow.collectAsState(initial = 10)
            val timeStateFlow = viewModel.stateFlow.collectAsState()
            val timeChannel = viewModel.channel.collectAsState(initial = 10)
            val timeSharedFlow = viewModel.sharedFlow.collectAsState(null)

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

                //Channels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            viewModel.triggerChannelData()
                        }
                    ) {
                        Text(text = "Channel Button")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = timeChannel.value.toString()
                    )
                }

                //SharedFlow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            viewModel.triggerSharedFlow()
                        }
                    ) {
                        Text(text = "SharedFlow Button")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = timeSharedFlow.value.toString()
                    )
                }
            }
        }
    }
}