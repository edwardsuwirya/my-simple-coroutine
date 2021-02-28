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
//                fakeHeavyProcessSimulation()
                fakeVeryHeavyProcessSimulation()
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

    suspend fun counterEvenOddCoroutine() {
        Log.d("Main", "Running on thread evenodd ${Thread.currentThread().name}")
        repeat(1000) {
            delay(500)
            if (it == 5) {
                throw Exception("Exception simulation")
            }
            if (it % 2 == 0) {
                updateEventOddCallback("$it is Even")
            } else {
                updateEventOddCallback("$it is Odd")
            }
        }
    }

    private fun updateCounterCallback(counter: Int) {
        binding.counterTextView.setText("$counter")
    }

    private fun updateEventOddCallback(evenOdd: String) {
        binding.evenOddTextView.setText(evenOdd)
    }

    private fun fakeHeavyProcessSimulation() {
        job = CoroutineScope(Dispatchers.Main).launch {
            counterCoroutine()
        }
    }

    private fun fakeVeryHeavyProcessSimulation() {
        val exceptionHandler =
            CoroutineExceptionHandler { coroutineContext, throwable -> Log.d("Main", "error") }
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            val childJob1 = launch { counterCoroutine() }
            val childJob2 = launch { counterEvenOddCoroutine() }
            Log.d("Main", "Finish")
        }
    }
}