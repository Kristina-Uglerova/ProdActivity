package com.example.prodactivity.viewModel

import androidx.lifecycle.ViewModel
import com.example.prodactivity.R
import com.example.prodactivity.data.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState(activity = R.string.app_name.toString()))
    val uiState : StateFlow<MainUiState> = _uiState.asStateFlow()
}