package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.diplomatest.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ProfileActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val TAG = "ProfileActivity"
    private var userListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadUserProfile()

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserProfile() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUid = currentUser?.uid

        if (currentUid != null) {
            val db = FirebaseFirestore.getInstance()
            val usersCollectionRef = db.collection("Users")

            userListener = usersCollectionRef.whereEqualTo("userId", currentUid)
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshots != null && !snapshots.isEmpty) {
                        val document = snapshots.documents[0]
                        val email = document.getString("email")
                        val imageUrl = document.getString("imageUrl")
                        val phone = document.getString("phone")
                        val username = document.getString("username")

                        updateUI(email, imageUrl, phone, username)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
        } else {
            Log.d(TAG, "No user is signed in")
        }
    }

    private fun updateUI(email: String?, imageUrl: String?, phone: String?, username: String?) {
        binding.emailTextView.text = email
        binding.usernameTextView.text = username
        binding.phoneTextView.text = phone
        Glide.with(this).load(imageUrl).into(binding.profileImageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        userListener?.remove()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
    }
}
