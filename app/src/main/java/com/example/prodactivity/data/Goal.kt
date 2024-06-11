package com.example.prodactivity.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "goals")
data class Goal (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String = "",
    var description: String = "",
    var isLongterm: Boolean = false,
    var streak: Int = 0,
    var showInfo: Boolean = false,
    val date: Long = Date().time,
    var isFinished: Boolean = false
    )