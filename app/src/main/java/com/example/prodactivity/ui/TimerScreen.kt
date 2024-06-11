package com.example.prodactivity.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prodactivity.R
import com.example.prodactivity.data.Timer
import com.example.prodactivity.ui.theme.ProdActivityTheme
import com.example.prodactivity.ui.theme.myDarkPink
import com.example.prodactivity.ui.theme.myLightGreen
import com.example.prodactivity.data.database.AppViewModelProvider
import com.example.prodactivity.viewModel.TimerViewModel
import kotlin.math.roundToInt

@Composable
fun TimerScreen(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var workTime by rememberSaveable { mutableStateOf(25) }
    var pauseTime by rememberSaveable { mutableStateOf(5) }
    var showTimerSettingsDialog by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(Timer()) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(screenWidth * 0.9f)
                .clip(CircleShape)
                .background(myLightGreen)
        ) {
            Text(text = viewModel.remainingTime, style = MaterialTheme.typography.displayLarge, color = myDarkPink)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { showTimerSettingsDialog = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.settings_icon),
                    contentDescription = stringResource(R.string.settings_icon)
                )
            }
            IconButton(
                onClick = { viewModel.startTimer(timer)  }
            ) {
                Icon(
                    painter = painterResource(R.drawable.play_icon),
                    contentDescription = stringResource(R.string.play_icon)
                )
            }
            IconButton(
                onClick = {
                    viewModel.startTimer(timer) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.restart_icon),
                    contentDescription = stringResource(R.string.restart_icon)
                )
            }
        }
    }
    if(showTimerSettingsDialog) {
        Dialog(
            onDismissRequest = { showTimerSettingsDialog = false },
            content = {
                Card(
                    modifier = Modifier
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.work_time))
                        Slider(
                            valueRange = 0f..59f,
                            value = workTime.toFloat(),
                            onValueChange = {
                                workTime = it.roundToInt()}
                        )
                        Text(text = workTime.toString())
                        Text(text = stringResource(id = R.string.pause_time))
                        Slider(
                            valueRange = 0f..59f,
                            value = pauseTime.toFloat(),
                            onValueChange = {
                                pauseTime = it.roundToInt()}
                        )
                        Text(text = pauseTime.toString())
                        Row() {
                            Button(onClick = {
                                timer.workTime = workTime
                                timer.pauseTime = pauseTime
                            }) {
                                Text(text = stringResource(id = R.string.ok_button))
                            }
                            Button(onClick = { showTimerSettingsDialog = false }) {
                                Text(text = stringResource(id = R.string.cancel_button))
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun TimersScreenPreview() {
    ProdActivityTheme {
        TimerScreen(
            modifier = Modifier.fillMaxHeight()
        )
    }
}
