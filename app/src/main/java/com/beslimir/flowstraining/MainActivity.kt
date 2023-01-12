package com.beslimir.flowstraining

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

//    val viewModel2: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //example without compose - without repeatOnLifecycle(Lifecycle.State.STARTED) it will
        //fire even if in background. In this way, it fires only when lifecycle is started state
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel2.sharedFlow.collect { str ->
//                    if (str == "Counter is 0!") {
//                        Toast.makeText(applicationContext, "aaa", Toast.LENGTH_SHORT).show()
//                        print("aaaaa")
//                    }
//                }
//            }
//        }

        setContent {
            val viewModel = viewModel<MyViewModel>()
            val timeFlow = viewModel.countDownFlow.collectAsState(initial = 10)
            val timeStateFlow = viewModel.stateFlow.collectAsState()
            val timeChannel = viewModel.channel.collectAsState(initial = 10)
            val timeSharedFlow = viewModel.sharedFlow.collectAsState(null)

            //example with compose
            LaunchedEffect(true) {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.sharedFlow.collectLatest { str ->
                        if (str == "Counter is 0!" || str == "Counter is 10!") {
                            Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

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