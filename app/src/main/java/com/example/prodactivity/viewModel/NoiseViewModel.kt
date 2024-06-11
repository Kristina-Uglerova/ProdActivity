package com.example.prodactivity.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prodactivity.R
import com.example.prodactivity.data.Noise

class NoiseViewModel : ViewModel() {
    private val _noises = MutableLiveData(mutableListOf<Noise>(
        Noise(1, R.string.blue_noise, R.raw.blue_noise, R.string.blue_noise_info),
        Noise(2, R.string.brown_noise, R.raw.brown_noise, R.string.brown_noise_info),
        Noise(3, R.string.green_noise, R.raw.green_noise, R.string.green_noise_info),
        Noise(4, R.string.grey_noise, R.raw.grey_noise, R.string.grey_noise_info),
        Noise(5, R.string.pink_noise, R.raw.pink_noise, R.string.pink_noise_info),
        Noise(6, R.string.violet_noise, R.raw.violet_noise, R.string.violet_noise_info),
        Noise(7, R.string.white_noise, R.raw.white_noise, R.string.white_noise_info)
    ))
    val noises: LiveData<MutableList<Noise>> = _noises

    fun updateNoiseItem(item: Noise) {
        _noises.value?.forEach {it.isPlaying = false}
        val foundItem = _noises.value?.filter { it.id == item.id }
        foundItem?.get(0)?.isPlaying = item.isPlaying
    }
}