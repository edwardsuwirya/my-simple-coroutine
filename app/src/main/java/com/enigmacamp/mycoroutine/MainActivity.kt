package com.enigmacamp.mycoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.enigmacamp.mycoroutine.databinding.ActivityMainBinding

/*
    - Di dalam pengembangan aplikasi android, jangan ada proses yang mem-blocking main thread

    - Main thread dalam android, adalah thread yang befungsi untuk meng-update screen UI,
      meng-handle event click, dan beberap UI callback

    - Update screen UI oleh Main Thread dilakukan dengan kecepatan 60 frame per detik (dengan kata lain
      proses update screen dilakukan per 16ms)

    - Banyak proses yang kita pakai nanti, lebih lambat dari update screen UI, seperti nulis data
      ke database, fetch data ke API, oleh karenanya apabila proses tersebut dilakukan di Main Thread
      dapat menyebabkan UI pause/freeze, dan kalau lebih parah, waktu freeze nya lama, bisa menyebabkan
      Application Not Responding

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
                isRunning = true
                fakeHeavyProcessSimulation()
            }
            stopButton.setOnClickListener {
                isRunning = false
            }
        }
    }

    private fun fakeHeavyProcessSimulation() {
        while (this.isRunning) {
            counterResult++
            Thread.sleep(1000)
            binding.counterTextView.setText(counterResult.toString())
        }
    }
}