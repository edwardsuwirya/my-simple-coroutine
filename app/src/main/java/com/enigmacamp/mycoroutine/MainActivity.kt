package com.enigmacamp.mycoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var counterTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    var counterResult = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        counterTextView = findViewById(R.id.counter_textView)
        startButton = findViewById(R.id.start_button)
        stopButton = findViewById(R.id.stop_button)
        startButton.setOnClickListener {
            repeat(1_000) {
                counterResult++
                Thread.sleep(1500)
                counterTextView.setText(counterResult.toString())
            }
        }
    }
}