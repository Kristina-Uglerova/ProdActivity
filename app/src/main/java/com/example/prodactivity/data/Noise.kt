package com.example.prodactivity.data

data class Noise(
    val id: Int,
    val nameResId: Int,
    val soundResId: Int,
    val infoResId: Int,
    var isPlaying: Boolean = false,
)
