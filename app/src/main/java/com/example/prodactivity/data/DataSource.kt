package com.example.prodactivity.data

import android.content.Context
import com.example.prodactivity.R

object DataSource {
    val noises = listOf(
        NoiseItem(R.string.blue_noise, R.raw.blue_noise, R.string.blue_noise_info),
        NoiseItem(R.string.brown_noise, R.raw.brown_noise, R.string.brown_noise_info),
        NoiseItem(R.string.green_noise, R.raw.green_noise, R.string.green_noise_info),
        NoiseItem(R.string.grey_noise, R.raw.grey_noise, R.string.grey_noise_info),
        NoiseItem(R.string.pink_noise, R.raw.pink_noise, R.string.pink_noise_info),
        NoiseItem(R.string.violet_noise, R.raw.violet_noise, R.string.violet_noise_info),
        NoiseItem(R.string.white_noise, R.raw.white_noise, R.string.white_noise_info)
    )
}