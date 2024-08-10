package com.example.diplomatest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.diplomatest.R
import com.example.diplomatest.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var editProfileImageView: ImageView
    private lateinit var editUsernameEditText: EditText
    private lateinit var editPhoneEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var db: FirebaseFirestore
    private var uri: Uri? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Initialize Firebase Auth, Firestore and Storage
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        db = FirebaseFirestore.getInstance()
        currentUser = auth.currentUser

        editProfileImageView = findViewById(R.id.editProfileImageView)
        editUsernameEditText = findViewById(R.id.editUsernameEditText)
        editPhoneEditText = findViewById(R.id.editPhoneEditText)
        saveButton = findViewById(R.id.saveButton)

        loadUserProfile()

        var galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                editProfileImageView.setImageURI(uri)
                this.uri = uri
            }
        }

        editProfileImageView.setOnClickListener {
            galleryImage.launch("image/*")
        }

        saveButton.setOnClickListener {
            saveUserProfile()
        }
    }

    private fun loadUserProfile() {
        val currentUid = currentUser?.uid

        if (currentUid != null) {
            val usersCollectionRef = db.collection("Users")

            usersCollectionRef.whereEqualTo("userId", currentUid)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val document = documents.documents[0]
                        val email = document.getString("email")
                        val imageUrl = document.getString("imageUrl")
                        val phone = document.getString("phone")
                        val username = document.getString("username")

                        updateUI(email, imageUrl, phone, username)
                    } else {
                        Toast.makeText(this, "No such document", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to get user data", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "No user is signed in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(email: String?, imageUrl: String?, phone: String?, username: String?) {
        editUsernameEditText.setText(username)
        editPhoneEditText.setText(phone)
        Glide.with(this).load(imageUrl).into(editProfileImageView)
    }

    private fun saveUserProfile() {
        val newUsername = editUsernameEditText.text.toString().trim()
        val newPhone = editPhoneEditText.text.toString().trim()

        val userUid = currentUser?.uid
        val usersCollectionRef = db.collection("Users")

        if (uri != null) {
            val storageRef = storage.reference.child("Users/${System.currentTimeMillis()}")

            storageRef.putFile(uri!!)
                .addOnSuccessListener { uploadTask ->
                    uploadTask.storage.downloadUrl
                        .addOnSuccessListener { uri ->
                            val updatedImageUrl = uri.toString()
                            if (userUid != null) {
                                usersCollectionRef.whereEqualTo("userId", userUid)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        if (!documents.isEmpty) {
                                            val document = documents.documents[0]
                                            val documentId = document.id
                                            val updatedUser = User(userUid, newUsername, currentUser?.email, newPhone, updatedImageUrl)
                                            usersCollectionRef.document(documentId).set(updatedUser)
                                                .addOnSuccessListener {
                                                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                                                    finish()
                                                }
                                                .addOnFailureListener {
                                                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    }
                            }
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
        } else {
            if (userUid != null) {
                usersCollectionRef.whereEqualTo("userId", userUid)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val document = documents.documents[0]
                            val documentId = document.id
                            val updatedUser = User(userUid, newUsername, currentUser?.email, newPhone, document.getString("imageUrl"))
                            usersCollectionRef.document(documentId).set(updatedUser)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
