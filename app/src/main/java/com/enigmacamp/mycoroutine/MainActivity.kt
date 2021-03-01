package com.enigmacamp.mycoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.enigmacamp.mycoroutine.databinding.ActivityMainBinding
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/*
    - Berikut contoh menggunakan thread terpisah

    - Coba simulasikan contoh sederhana dibawah ini

 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val delayTime = 1000
    var totalTime = 0
    var maxNum = 0
    var counterResult = 0
    var isRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            startButton.setOnClickListener {
                val num = numEditText.text
                maxNum = num.toString().toInt()
                if (counterResult == maxNum + 1) {
                    reset()
                }
                evenOddCalculation()

                Log.d("Main", "Running on thread ${Thread.currentThread().name}")
            }
            stopButton.setOnClickListener {
                isRunning = false
            }
        }
    }

    private fun reset() {
        counterResult = 0
        totalTime = 0
        binding.counterTextView.setText("")
        binding.timeExecTextView.setText("Total Time:")
    }

    private fun updateCounterCallback(counter: String) {
        runOnUiThread {
            binding.counterTextView.setText(counter)
        }
    }

    private fun updateTotalTimeCallback() {
        runOnUiThread {
            binding.timeExecTextView.setText("$totalTime")
        }
    }

    private fun evenOddCalculation() {
        isRunning = true
        thread(true) {
            Log.d("Main", "Running on thread ${Thread.currentThread().name}")
            while (this.isRunning) {

                if (counterResult <= maxNum) {
                    if (counterResult % 2 == 0) {
                        updateCounterCallback("$counterResult is even")
                    } else {
                        updateCounterCallback("$counterResult is odd")
                    }
                    printTime()
                    counterResult++
                } else {
                    this.isRunning = false
                }
                Thread.sleep(delayTime.toLong())
            }
        }
    }

    private fun printTime() {
        Log.d("Main", (delayTime * counterResult).toString())
        totalTime = (delayTime * (counterResult + 1))
        updateTotalTimeCallback()
    }
}