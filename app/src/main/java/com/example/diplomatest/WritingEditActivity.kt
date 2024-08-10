package com.example.diplomatest

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.databinding.ActivityWritingEditBinding
import com.example.diplomatest.model.WritingTest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class WritingEditActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWritingEditBinding

    private var uri: Uri? = null

    private lateinit var et1: EditText
    private lateinit var et2: EditText
    private lateinit var wordCount1: TextView
    private lateinit var wordCount2: TextView

    private lateinit var edit: Button

    lateinit var mDataBase: FirebaseFirestore
    lateinit var storage: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityWritingEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id = intent.extras?.getString("id") ?: error("not found")




        storage = FirebaseStorage.getInstance()



        et1 = findViewById(R.id.largeEditText1)
        et2 = findViewById(R.id.largeEditText2)
        wordCount1 = findViewById(R.id.wordCountTextView1)
        wordCount2 = findViewById(R.id.wordCountTextView2)

        // Ensure EditText is scrollable
        et1.movementMethod = ScrollingMovementMethod()
        et1.isVerticalScrollBarEnabled = true

        // Set the EditText to be focusable and scrollable
        et1.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }

        // Make text selectable
        et1.setTextIsSelectable(true)


        et1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if text is empty
                if (s.isNullOrEmpty()) {
                    wordCount1.text = "Word count: 0"
                } else {
                    // Update the word count whenever text changes
                    val wordCountFirst = s.trim().split("\\s+".toRegex()).size
                    wordCount1.text = "Word count: $wordCountFirst"
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No implementation needed
            }
        })


        // Ensure EditText is scrollable
        et2.movementMethod = ScrollingMovementMethod()
        et2.isVerticalScrollBarEnabled = true

        // Set the EditText to be focusable and scrollable
        et2.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }

        // Make text selectable
        et2.setTextIsSelectable(true)


        et2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if text is empty
                if (s.isNullOrEmpty()) {
                    wordCount2.text = "Word count: 0"
                } else {
                    // Update the word count whenever text changes
                    val wordCountSecond = s.trim().split("\\s+".toRegex()).size
                    wordCount2.text = "Word count: $wordCountSecond"
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No implementation needed
            }
        })



        edit = findViewById(R.id.edit_writing)



        edit.setOnClickListener {
            editTest(id)
        }

        // Fetch data from Firestore and load images
        fetchDataAndLoadImages(id)

        // Fetch existing answers from Firestore and populate EditText fields
        fetchExistingAnswers(id)


    }

    private fun fetchExistingAnswers(id: String) {
        mDataBase.collection("WritingTests").document(id).get()
            .addOnSuccessListener { documentSnapshot ->
                val writingTest = documentSnapshot.toObject(WritingTest::class.java)
                writingTest?.let {
                    // Check if q1 and q2 fields exist in the document
                    val q1 = it.q1
                    val q2 = it.q2
                    // Populate EditText fields if q1 and q2 exist
                    q1?.let { answer1 ->
                        et1.setText(answer1)
                    }
                    q2?.let { answer2 ->
                        et2.setText(answer2)
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error loading existing answers: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchDataAndLoadImages(id: String) {
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("WritingTests").document(id).get()
            .addOnSuccessListener { documentSnapshot ->
                val writingTest = documentSnapshot.toObject(WritingTest::class.java)
                writingTest?.let {
                    val img1 = it.img1
                    val img2 = it.img2

                    loadImages(img1, img2)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error loading images: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadImages(img1: String?, img2: String?) {
        val imageView1 = findViewById<AspectRatioImageView>(R.id.img1)
        val imageView2 = findViewById<AspectRatioImageView>(R.id.img2)

        if (!img1.isNullOrEmpty()) {
            Picasso.get().load(img1).into(imageView1, object : Callback {
                override fun onSuccess() {
                    val aspectRatio = imageView1.drawable.intrinsicWidth.toFloat() / imageView1.drawable.intrinsicHeight.toFloat()
                    imageView1.setAspectRatio(aspectRatio)
                }

                override fun onError(e: Exception?) {
                    imageView1.setImageResource(R.drawable.img_7)
                }
            })
        } else {
            imageView1.setImageResource(R.drawable.img_7)
        }

        if (!img2.isNullOrEmpty()) {
            Picasso.get().load(img2).into(imageView2, object : Callback {
                override fun onSuccess() {
                    val aspectRatio = imageView2.drawable.intrinsicWidth.toFloat() / imageView2.drawable.intrinsicHeight.toFloat()
                    imageView2.setAspectRatio(aspectRatio)
                }

                override fun onError(e: Exception?) {
                    imageView2.setImageResource(R.drawable.img_7)
                }
            })
        } else {
            imageView2.setImageResource(R.drawable.img_7)
        }
    }

    fun editTest(id: String) {
        var storageRef = storage.reference.child("WritingTests")
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserID = currentUser?.uid

        if (currentUserID == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        storageRef = storageRef.child(id) // Assuming apartmentId is the ID of the apartment you want to edit

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("Users")
            .whereEqualTo("userId", currentUserID)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val userDocument = querySnapshot.documents[0]
                val username = userDocument.getString("username")
                if (username == null) {
                    Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                mDataBase = FirebaseFirestore.getInstance()
                mDataBase.collection("WritingTests")
                    .document(id)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val existingData = documentSnapshot.toObject(WritingTest::class.java)

                        if (existingData == null) {
                            Toast.makeText(this, "Error: Document does not exist", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        val q1 = et1.text.toString().trim()
                        val q2 = et2.text.toString().trim()
                        val wordCount1 = wordCount1.text.toString().trim()
                        val wordCount2 = wordCount2.text.toString().trim()

                        val newData = WritingTest(
                            username,
                            currentUserID,
                            id,
                            existingData?.part ?: "",
                            existingData?.img1?: "",
                            existingData?.img2?: "",
                            if (q1.isNotEmpty()) q1 else existingData.q1 ?: "",
                            if (q2.isNotEmpty()) q2 else existingData.q2 ?: "",
                            if (wordCount1.isNotEmpty()) wordCount1 else existingData.wordCount1 ?: "",
                            if (wordCount2.isNotEmpty()) wordCount2 else existingData.wordCount2 ?: "",
                            existingData?.feedback1 ?: "",
                            existingData?.feedback2 ?: ""
                        )

                        // Check if document already exists in WritingTestsAnswers
                        mDataBase.collection("WritingTestsAnswers")
                            .whereEqualTo("studentId", currentUserID)
                            .whereEqualTo("id", id)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                if (querySnapshot.isEmpty) {
                                    // Document does not exist, create a new one
                                    mDataBase.collection("WritingTestsAnswers")
                                        .add(newData)
                                        .addOnSuccessListener {
                                            overridePendingTransition(
                                                R.anim.slide_enter_left,
                                                R.anim.slide_exit_right
                                            )
                                            finish()
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(this, "Error saving new data: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                } else {
                                    // Document exists, update the existing one
                                    val existingDocumentId = querySnapshot.documents[0].id
                                    mDataBase.collection("WritingTestsAnswers")
                                        .document(existingDocumentId)
                                        .set(newData)
                                        .addOnSuccessListener {
                                            overridePendingTransition(
                                                R.anim.slide_enter_left,
                                                R.anim.slide_exit_right
                                            )
                                            finish()
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(this, "Error updating data: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error checking existing data: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error fetching existing data: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error fetching username: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }



    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}