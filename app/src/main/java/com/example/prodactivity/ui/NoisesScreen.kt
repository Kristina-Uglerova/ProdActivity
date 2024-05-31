package com.example.prodactivity.ui

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.prodactivity.R
import com.example.prodactivity.data.NoiseItem
import com.example.prodactivity.ui.theme.ProdActivityTheme

@Composable
fun NoisesScreen(
    options: List<NoiseItem>,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var actualMediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }
    var noiseName by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            options.forEach { item ->
                Row(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(item.nameResId),
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            if (actualMediaPlayer != null && actualMediaPlayer!!.isPlaying) {
                                actualMediaPlayer!!.pause()
                            }
                            item.playPause(context)
                            isPlaying = item.isPlaying
                            if (item.mediaPlayer!!.isPlaying) {
                                actualMediaPlayer = item.mediaPlayer
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (item.isPlaying) R.drawable.noise_pause_icon else R.drawable.noise_play_icon),
                            contentDescription = stringResource(
                                if (isPlaying) R.string.pause_icon else R.string.play_icon),
                        )
                    }
                    IconButton(
                        onClick = {
                            dialogText = context.getString(item.infoResId)
                            noiseName = context.getString(item.nameResId)
                            showDialog = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.noise_info_icon),
                            contentDescription = stringResource(R.string.info_icon)
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            content = {
                Card(
                    modifier = Modifier
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            noiseName,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            dialogText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun NoisesScreenPreview() {
    ProdActivityTheme {

    }
}
