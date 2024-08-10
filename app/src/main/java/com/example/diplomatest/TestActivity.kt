package com.example.diplomatest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class TestActivity: AppCompatActivity() {

    private lateinit var btnListening: CardView

    private lateinit var btnReading: CardView

    private lateinit var btnWriting: CardView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        btnListening = findViewById(R.id.listening_btn)

        btnReading = findViewById(R.id.reading_btn)

        btnWriting = findViewById(R.id.writing_btn)

        btnListening.setOnClickListener {
            val intent = Intent(this@TestActivity, ListeningActivity::class.java)
            startActivity(intent)
        }

        btnReading.setOnClickListener {
            val intent = Intent(this@TestActivity, ReadingActivity::class.java)
            startActivity(intent)
        }

        btnWriting.setOnClickListener {
            val intent = Intent(this@TestActivity, WritingActivity::class.java)
            startActivity(intent)
        }
    }

}