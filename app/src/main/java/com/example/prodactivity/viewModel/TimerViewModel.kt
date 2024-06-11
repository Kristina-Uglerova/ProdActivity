package com.example.prodactivity.viewModel

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.prodactivity.data.Timer

class TimerViewModel : ViewModel() {
    private var countDownTimer: CountDownTimer? = null
    var remainingTime = "00:00"
    var test = false
    fun startTimer(timer: Timer) {
        val absoluteTime = (timer.workTime + timer.pauseTime).toLong() * 60000
        countDownTimer = object : CountDownTimer(absoluteTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = String.format("%02d:%02d", millisUntilFinished / 60000, (millisUntilFinished % 60000) / 1000)
                println(remainingTime)
                test = !test
            }

            override fun onFinish() {
                println("finished")
            }

        }
        countDownTimer!!.start()

    }
    fun restartTimer() {
        countDownTimer?.cancel()
        countDownTimer?.start()
    }
    fun stopTimer() {
        countDownTimer?.cancel()
    }
}

