package com.example.prodactivity.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodactivity.data.Goal
import com.example.prodactivity.data.database.GoalsRepository
import com.example.prodactivity.ui.GoalEditDialogParameters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GoalsViewModel(private val goalsRepository: GoalsRepository) : ViewModel() {

    private val _parameters: MutableStateFlow<GoalEditDialogParameters> = MutableStateFlow(
        GoalEditDialogParameters()
    )
    val parameters = _parameters.asStateFlow()
    val goalsUiState: StateFlow<GoalsUiState> =
        goalsRepository.getAllItemsStream().map { GoalsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GoalsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    suspend fun saveGoal(title: String, description: String, isLongterm: Boolean) {
        goalsRepository.insertItem(Goal(title = title, description = description, isLongterm = isLongterm))
    }

    suspend fun updateGoal(goal: Goal) {
        goalsRepository.updateItem(goal)
    }

    suspend fun deleteGoal(goal: Goal) {
        goalsRepository.deleteItem(goal)
    }

    suspend fun isGoalValid(title: String, originalTitle: String): Boolean {
        val existingGoal = goalsRepository.getGoalByTitle(title).firstOrNull()
        return title.isNotEmpty() && (existingGoal == null || title == originalTitle)
    }

    fun editParameters(parameters: GoalEditDialogParameters) {
        _parameters.value = parameters
    }
}

data class GoalsUiState(val itemList: List<Goal> = listOf())
