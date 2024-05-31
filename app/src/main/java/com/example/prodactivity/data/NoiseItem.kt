package com.example.prodactivity.data

import android.content.Context
import android.media.MediaPlayer

data class NoiseItem(
    val nameResId: Int,
    val soundResId: Int,
    val infoResId: Int,
    var isPlaying: Boolean = false,
    var mediaPlayer: MediaPlayer? = null
) {

    fun playPause(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, soundResId)
            mediaPlayer!!.isLooping = true
        }
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
        } else {
            mediaPlayer!!.start()
        }
        isPlaying = mediaPlayer!!.isPlaying
    }
}
