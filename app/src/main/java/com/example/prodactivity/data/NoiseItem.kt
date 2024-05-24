package com.example.prodactivity.data

import android.content.Context
import android.media.MediaPlayer

data class NoiseItem(
    val nameResId: Int,
    val soundResId: Int,
    var isPlaying: Boolean = false,
    var mediaPlayer: MediaPlayer? = null
) {
    fun initializeMediaPlayer(context: Context) {
        mediaPlayer = MediaPlayer.create(context, soundResId)
    }
    fun playPause(context: Context) {
        if (mediaPlayer == null) {
            initializeMediaPlayer(context)
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
