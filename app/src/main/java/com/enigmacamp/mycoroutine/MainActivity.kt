package com.enigmacamp.mycoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.enigmacamp.mycoroutine.databinding.ActivityMainBinding
import kotlin.concurrent.thread

/*
    - Berikut contoh menggunakan thread terpisah

    - Coba simulasikan contoh sederhana dibawah ini

 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var counterResult = 0
    var isRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            counterTextView
            startButton.setOnClickListener {
                fakeHeavyProcessSimulation()
                Log.d("Main", "Running on thread ${Thread.currentThread().name}")
            }
            stopButton.setOnClickListener {
                isRunning = false
            }
        }
    }

    fun seperateThread() {
        isRunning = true
        thread(true) {
            Log.d("Main", "Running on thread ${Thread.currentThread().name}")
            while (this.isRunning) {
                counterResult++
                Thread.sleep(1000)
                runOnUiThread { binding.counterTextView.setText(counterResult.toString()) }
            }
        }
    }

    private fun fakeHeavyProcessSimulation() {
        seperateThread()
    }
}