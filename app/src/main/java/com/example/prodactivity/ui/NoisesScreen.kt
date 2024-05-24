package com.example.prodactivity.ui

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.prodactivity.R
import com.example.prodactivity.data.NoiseItem
import com.example.prodactivity.ui.theme.ProdActivityTheme
@Composable
fun NoisesScreen(
    options: List<NoiseItem>,
    onSelectionChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }
    var isPlaying by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var actualMediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            options.forEach { item ->
                Row(
                    modifier = Modifier.selectable(
                        selected = selectedValue == item.nameResId.toString(),
                        onClick = {
                            selectedValue = item.nameResId.toString()
                            onSelectionChanged(item.nameResId.toString())
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(item.nameResId))
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            //if(actualMediaPlayer != null && actualMediaPlayer!!.isPlaying) {
                              //  actualMediaPlayer!!.stop()
                            //}
                            item.playPause(context)
                            isPlaying = item.isPlaying;
                            //if(item.mediaPlayer!!.isPlaying) {
                              //  actualMediaPlayer = item.mediaPlayer
                            //}
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (item.isPlaying) R.drawable.noise_pause_icon else R.drawable.noise_play_icon
                            ),
                            contentDescription = stringResource(
                                if (isPlaying) R.string.pause_icon else R.string.play_icon
                            )
                        )
                    }
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.noise_info_icon),
                            contentDescription = stringResource(R.string.info_icon)
                        )
                    }
                    //RadioButton(
                        //selected = selectedValue == item,
                        //onClick = {
                           // selectedValue = item
                            //onSelectionChanged(item)
                        //}
                    //)
                }
            }
        }
    }
}

@Preview
@Composable
fun NoisesScreenPreview() {
    ProdActivityTheme {
        //NoisesScreen(
            //options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
            //modifier = Modifier.fillMaxHeight()
        //)
    }
}