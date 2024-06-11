package com.example.prodactivity.data.database

import com.example.prodactivity.data.Goal
import kotlinx.coroutines.flow.Flow

class OfflineGoalsRepository(private val goalDao: GoalDao) : GoalsRepository {
    override fun getAllItemsStream(): Flow<List<Goal>> = goalDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Goal?> = goalDao.getItem(id)

    override suspend fun insertItem(goal: Goal) = goalDao.insert(goal)

    override suspend fun deleteItem(goal: Goal) = goalDao.delete(goal)

    override suspend fun updateItem(goal: Goal) = goalDao.update(goal)
    override fun getFinishedGoalsCount(): Flow<Int> = goalDao.getFinishedGoalsCount()

    override fun getNotFinishedGoalsCount(): Flow<Int> = goalDao.getNotFinishedGoalsCount()

    override fun getMaxStreakGoal(): Flow<Goal> = goalDao.getMaxStreakGoal()

    override fun getGoalsCount(): Flow<Int> = goalDao.getGoalsCount()

    override fun getGoalByTitle(title: String): Flow<Goal> = goalDao.getGoalByName(title)

    override fun getTopFiveStreaks(): Flow<List<Goal>> = goalDao.getTopFiveStreaks()
}