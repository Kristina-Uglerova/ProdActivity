package com.example.prodactivity.data


data class Goal (
    var goalName: String = "",
    var goalDescription: String = "",
    var isLongTerm: Boolean = false,
    var streak: Int = 100,
    var showGoalInfo: Boolean = false
    )