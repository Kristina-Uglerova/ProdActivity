package com.example.prodactivity.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodactivity.data.Goal
import com.example.prodactivity.data.database.GoalsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class StatsViewModel(private val goalsRepository: GoalsRepository) : ViewModel() {
    val statsUiState: StateFlow<StatsUiState> =
        goalsRepository.getTopFiveStreaks().map { StatsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = StatsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val maxStreakGoal: StateFlow<Goal?> = goalsRepository.getMaxStreakGoal()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), null)

    val finishedGoalsCount: StateFlow<Int?> = goalsRepository.getFinishedGoalsCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), 0)

    val notFinishedGoalsCount: StateFlow<Int?> = goalsRepository.getNotFinishedGoalsCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), 0)

    private val goalsCount: StateFlow<Int> = goalsRepository.getGoalsCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), 0)

    val finishedGoalsPercentage: StateFlow<Float> = goalsCount.map { total ->
        if (total > 0) {
            ((finishedGoalsCount.value?.toFloat() ?: 0f) / total) * 100
        } else {
            0f
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), 0f)

    fun calculateGoalWidth(goalStreak: Int): Float {
        return try {
            if (maxStreakGoal.value?.streak!! > 0) {
                (goalStreak.toFloat() / maxStreakGoal.value!!.streak)
            } else {
                0f
            }
        } catch (e: NullPointerException) {
            0f
        }

    }
}

data class StatsUiState(val itemList: List<Goal> = listOf())