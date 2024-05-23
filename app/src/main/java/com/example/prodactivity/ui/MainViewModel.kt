package com.example.prodactivity.ui

import androidx.lifecycle.ViewModel
import com.example.prodactivity.data.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState(activity = "test"))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun setActivityName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                activity = name
            )
        }
    }
}