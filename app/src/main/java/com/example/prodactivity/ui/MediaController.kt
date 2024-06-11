package com.example.prodactivity.ui

import android.content.Context
import android.media.MediaPlayer

class MediaController {
    var mediaPlayer: MediaPlayer? = null
    var actualResId: Int? = null

    companion object {
        private var instance: MediaController? = null
        @Synchronized
        fun getInstance(): MediaController? {
            if (instance == null) {
                instance = MediaController()
            }
            return instance
        }
    }
    fun playPause(context: Context, soundResId: Int) {
        if(soundResId == actualResId) {
            if(mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
            } else {
                mediaPlayer?.start()
            }
        } else {
            actualResId = soundResId
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, soundResId)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }
}


