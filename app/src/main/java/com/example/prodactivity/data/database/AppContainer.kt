package com.example.prodactivity.data.database

import android.content.Context


interface AppContainer {
    val goalsRepository: GoalsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val goalsRepository: GoalsRepository by lazy {
        OfflineGoalsRepository(GoalDatabase.getDatabase(context).goalDao())
    }
}
