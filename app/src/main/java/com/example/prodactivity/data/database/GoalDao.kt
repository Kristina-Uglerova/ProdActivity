package com.example.prodactivity.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.prodactivity.data.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * from goals ORDER BY date ASC")
    fun getAllItems(): Flow<List<Goal>>

    @Query("SELECT * from goals WHERE id = :id")
    fun getItem(id: Int): Flow<Goal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(goal: Goal)

    @Update
    suspend fun update(goal: Goal)

    @Delete
    suspend fun delete(goal: Goal)

    @Query("SELECT COUNT(*) FROM goals WHERE isFinished = 1")
    fun getFinishedGoalsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM goals WHERE isFinished = 0")
    fun getNotFinishedGoalsCount(): Flow<Int>

    @Query("SELECT * FROM goals WHERE isLongterm = 1 AND isFinished = 0 ORDER BY streak DESC LIMIT 1")
    fun getMaxStreakGoal(): Flow<Goal>

    @Query("SELECT * FROM goals WHERE isLongterm = 1 AND isFinished = 0 AND streak > 0 ORDER BY streak DESC LIMIT 5")
    fun getTopFiveStreaks(): Flow<List<Goal>>

    @Query("SELECT COUNT(*) FROM goals")
    fun getGoalsCount(): Flow<Int>

    @Query("SELECT * from goals WHERE title = :title AND isFinished = 0")
    fun getGoalByName(title: String): Flow<Goal>
}