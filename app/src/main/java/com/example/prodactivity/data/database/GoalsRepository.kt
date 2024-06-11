package com.example.prodactivity.data.database

import com.example.prodactivity.data.Goal
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {

    fun getAllItemsStream(): Flow<List<Goal>>

    fun getItemStream(id: Int): Flow<Goal?>

    suspend fun insertItem(goal: Goal)

    suspend fun deleteItem(goal: Goal)

    suspend fun updateItem(goal: Goal)

    fun getFinishedGoalsCount(): Flow<Int>

    fun getNotFinishedGoalsCount(): Flow<Int>

    fun getMaxStreakGoal(): Flow<Goal>

    fun getGoalsCount(): Flow<Int>

    fun getGoalByTitle(title: String): Flow<Goal>

    fun getTopFiveStreaks(): Flow<List<Goal>>


}