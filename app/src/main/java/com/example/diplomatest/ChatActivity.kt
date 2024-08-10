package com.example.diplomatest

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.databinding.ActivityChatBinding
import com.example.diplomatest.databinding.ActivityMainBinding

class ChatActivity: AppCompatActivity() {

    private lateinit var binding : ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}