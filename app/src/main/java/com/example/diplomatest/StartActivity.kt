package com.example.diplomatest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class StartActivity: AppCompatActivity() {

    private lateinit var btnStudent: CardView

    private lateinit var btnAdmin: CardView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        btnStudent = findViewById(R.id.student_btn)

        btnAdmin = findViewById(R.id.admin_btn)

        btnStudent.setOnClickListener {
            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnAdmin.setOnClickListener {
            val intent = Intent(this@StartActivity, AdminActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}