package com.enigmacamp.mycoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.enigmacamp.mycoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.concurrent.thread

/*
    - Berikut contoh menggunakan Coroutine

    - Coba simulasikan contoh sederhana dibawah ini

 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var counterResult = 0
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            startButton.setOnClickListener {
                fakeHeavyProcessSimulation()
                Log.d("Main", "Running on thread ${Thread.currentThread().name}")
            }
            stopButton.setOnClickListener {
                job.cancel()
            }
        }
    }

    suspend fun counterCoroutine() {
        Log.d("Main", "Running on thread ${Thread.currentThread().name}")
        while (job.isActive) {
            counterResult++
            delay(1000)
            updateCounterCallback(counterResult)
        }
    }

    private fun updateCounterCallback(counter: Int) {
        binding.counterTextView.setText("$counter")
    }


    private fun fakeHeavyProcessSimulation() {
        job = CoroutineScope(Dispatchers.Main).launch {
            counterCoroutine()
        }
    }
}