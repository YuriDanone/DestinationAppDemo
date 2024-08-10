package com.example.diplomatest

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.databinding.ActivityReadingEditBinding
import com.example.diplomatest.model.ReadingTest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ReadingEditActivity: AppCompatActivity() {
    private lateinit var binding: ActivityReadingEditBinding

    private var uri: Uri? = null

    private lateinit var et1: EditText
    private lateinit var et2: EditText
    private lateinit var et3: EditText
    private lateinit var et4: EditText
    private lateinit var et5: EditText
    private lateinit var et6: EditText
    private lateinit var et7: EditText
    private lateinit var et8: EditText
    private lateinit var et9: EditText
    private lateinit var et10: EditText
    private lateinit var et11: EditText
    private lateinit var et12: EditText
    private lateinit var et13: EditText
    private lateinit var et14: EditText
    private lateinit var et15: EditText
    private lateinit var et16: EditText
    private lateinit var et17: EditText
    private lateinit var et18: EditText
    private lateinit var et19: EditText
    private lateinit var et20: EditText
    private lateinit var et21: EditText
    private lateinit var et22: EditText
    private lateinit var et23: EditText
    private lateinit var et24: EditText
    private lateinit var et25: EditText
    private lateinit var et26: EditText
    private lateinit var et27: EditText
    private lateinit var et28: EditText
    private lateinit var et29: EditText
    private lateinit var et30: EditText
    private lateinit var et31: EditText
    private lateinit var et32: EditText
    private lateinit var et33: EditText
    private lateinit var et34: EditText
    private lateinit var et35: EditText
    private lateinit var et36: EditText
    private lateinit var et37: EditText
    private lateinit var et38: EditText
    private lateinit var et39: EditText
    private lateinit var et40: EditText

    private lateinit var edit: Button

    lateinit var mDataBase: FirebaseFirestore
    lateinit var storage: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityReadingEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id = intent.extras?.getString("id") ?: error("not found")




        storage = FirebaseStorage.getInstance()



        et1 = findViewById(R.id.editText1)
        et2 = findViewById(R.id.editText2)
        et3 = findViewById(R.id.editText3)
        et4 = findViewById(R.id.editText4)
        et5 = findViewById(R.id.editText5)
        et6 = findViewById(R.id.editText6)
        et7 = findViewById(R.id.editText7)
        et8 = findViewById(R.id.editText8)
        et9 = findViewById(R.id.editText9)
        et10 = findViewById(R.id.editText10)
        et11 = findViewById(R.id.editText11)
        et12 = findViewById(R.id.editText12)
        et13 = findViewById(R.id.editText13)
        et14 = findViewById(R.id.editText14)
        et15 = findViewById(R.id.editText15)
        et16 = findViewById(R.id.editText16)
        et17 = findViewById(R.id.editText17)
        et18 = findViewById(R.id.editText18)
        et19 = findViewById(R.id.editText19)
        et20 = findViewById(R.id.editText20)
        et21 = findViewById(R.id.editText21)
        et22 = findViewById(R.id.editText22)
        et23 = findViewById(R.id.editText23)
        et24 = findViewById(R.id.editText24)
        et25 = findViewById(R.id.editText25)
        et26 = findViewById(R.id.editText26)
        et27 = findViewById(R.id.editText27)
        et28 = findViewById(R.id.editText28)
        et29 = findViewById(R.id.editText29)
        et30 = findViewById(R.id.editText30)
        et31 = findViewById(R.id.editText31)
        et32 = findViewById(R.id.editText32)
        et33 = findViewById(R.id.editText33)
        et34 = findViewById(R.id.editText34)
        et35 = findViewById(R.id.editText35)
        et36 = findViewById(R.id.editText36)
        et37 = findViewById(R.id.editText37)
        et38 = findViewById(R.id.editText38)
        et39 = findViewById(R.id.editText39)
        et40 = findViewById(R.id.editText40)


        edit = findViewById(R.id.edit_listening)


//        var galleryImage2 = registerForActivityResult(
//            ActivityResultContracts.GetContent(),
//            {
//                ivImage2.setImageURI(it)
//                uri = it!!
//            }
//        )

//        var audioFileLauncher = registerForActivityResult(
//            ActivityResultContracts.GetContent(),
//            {
//                uriAudio = it!!
//                // You may want to handle the audio file here, like previewing it
//                // For example:
//                // val mediaPlayer = MediaPlayer.create(this, uriAudio)
//                // mediaPlayer.start()
//            }
//        )

//        ivAudio.setOnClickListener {
//            audioFileLauncher.launch("audio/*")
//        }



//        ivImage2.setOnClickListener {
//            galleryImage2.launch("image/*")
//        }

        edit.setOnClickListener {
            editApartment(id)
        }

    }

    fun editApartment(id: String) {
        var storageRef = storage.reference.child("ReadingTests")
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserID = currentUser?.uid

        if (currentUserID == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        storageRef = storageRef.child(id)

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

                val q1 = et1.text.toString().trim()
                val q2 = et2.text.toString().trim()
                val q3 = et3.text.toString().trim()
                val q4 = et4.text.toString().trim()
                val q5 = et5.text.toString().trim()
                val q6 = et6.text.toString().trim()
                val q7 = et7.text.toString().trim()
                val q8 = et8.text.toString().trim()
                val q9 = et9.text.toString().trim()
                val q10 = et10.text.toString().trim()
                val q11 = et11.text.toString().trim()
                val q12 = et12.text.toString().trim()
                val q13 = et13.text.toString().trim()
                val q14 = et14.text.toString().trim()
                val q15 = et15.text.toString().trim()
                val q16 = et16.text.toString().trim()
                val q17 = et17.text.toString().trim()
                val q18 = et18.text.toString().trim()
                val q19 = et19.text.toString().trim()
                val q20 = et20.text.toString().trim()
                val q21 = et21.text.toString().trim()
                val q22 = et22.text.toString().trim()
                val q23 = et23.text.toString().trim()
                val q24 = et24.text.toString().trim()
                val q25 = et25.text.toString().trim()
                val q26 = et26.text.toString().trim()
                val q27 = et27.text.toString().trim()
                val q28 = et28.text.toString().trim()
                val q29 = et29.text.toString().trim()
                val q30 = et30.text.toString().trim()
                val q31 = et31.text.toString().trim()
                val q32 = et32.text.toString().trim()
                val q33 = et33.text.toString().trim()
                val q34 = et34.text.toString().trim()
                val q35 = et35.text.toString().trim()
                val q36 = et36.text.toString().trim()
                val q37 = et37.text.toString().trim()
                val q38 = et38.text.toString().trim()
                val q39 = et39.text.toString().trim()
                val q40 = et40.text.toString().trim()

                mDataBase = FirebaseFirestore.getInstance()

                mDataBase.collection("ReadingTests")
                    .document(id)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val existingData = documentSnapshot.toObject(ReadingTest::class.java)

                        val newData = ReadingTest(
                            username,
                            currentUserID,
                            id,
                            existingData?.part ?: "",
                            existingData?.img1 ?: "",
                            existingData?.img2 ?: "",
                            existingData?.img3 ?: "",
                            if (q1.isNotEmpty()) q1 else existingData?.q1 ?: "",
                            if (q2.isNotEmpty()) q2 else existingData?.q2 ?: "",
                            if (q3.isNotEmpty()) q3 else existingData?.q3 ?: "",
                            if (q4.isNotEmpty()) q4 else existingData?.q4 ?: "",
                            if (q5.isNotEmpty()) q5 else existingData?.q5 ?: "",
                            if (q6.isNotEmpty()) q6 else existingData?.q6 ?: "",
                            if (q7.isNotEmpty()) q7 else existingData?.q7 ?: "",
                            if (q8.isNotEmpty()) q8 else existingData?.q8 ?: "",
                            if (q9.isNotEmpty()) q9 else existingData?.q9 ?: "",
                            if (q10.isNotEmpty()) q10 else existingData?.q10 ?: "",
                            if (q11.isNotEmpty()) q11 else existingData?.q11 ?: "",
                            if (q12.isNotEmpty()) q12 else existingData?.q12 ?: "",
                            if (q13.isNotEmpty()) q13 else existingData?.q13 ?: "",
                            if (q14.isNotEmpty()) q14 else existingData?.q14 ?: "",
                            if (q15.isNotEmpty()) q15 else existingData?.q15 ?: "",
                            if (q16.isNotEmpty()) q16 else existingData?.q16 ?: "",
                            if (q17.isNotEmpty()) q17 else existingData?.q17 ?: "",
                            if (q18.isNotEmpty()) q18 else existingData?.q18 ?: "",
                            if (q19.isNotEmpty()) q19 else existingData?.q19 ?: "",
                            if (q20.isNotEmpty()) q20 else existingData?.q20 ?: "",
                            if (q21.isNotEmpty()) q21 else existingData?.q21 ?: "",
                            if (q22.isNotEmpty()) q22 else existingData?.q22 ?: "",
                            if (q23.isNotEmpty()) q23 else existingData?.q23 ?: "",
                            if (q24.isNotEmpty()) q24 else existingData?.q24 ?: "",
                            if (q25.isNotEmpty()) q25 else existingData?.q25 ?: "",
                            if (q26.isNotEmpty()) q26 else existingData?.q26 ?: "",
                            if (q27.isNotEmpty()) q27 else existingData?.q27 ?: "",
                            if (q28.isNotEmpty()) q28 else existingData?.q28 ?: "",
                            if (q29.isNotEmpty()) q29 else existingData?.q29 ?: "",
                            if (q30.isNotEmpty()) q30 else existingData?.q30 ?: "",
                            if (q31.isNotEmpty()) q31 else existingData?.q31 ?: "",
                            if (q32.isNotEmpty()) q32 else existingData?.q32 ?: "",
                            if (q33.isNotEmpty()) q33 else existingData?.q33 ?: "",
                            if (q34.isNotEmpty()) q34 else existingData?.q34 ?: "",
                            if (q35.isNotEmpty()) q35 else existingData?.q35 ?: "",
                            if (q36.isNotEmpty()) q36 else existingData?.q36 ?: "",
                            if (q37.isNotEmpty()) q37 else existingData?.q37 ?: "",
                            if (q38.isNotEmpty()) q38 else existingData?.q38 ?: "",
                            if (q39.isNotEmpty()) q39 else existingData?.q39 ?: "",
                            if (q40.isNotEmpty()) q40 else existingData?.q40 ?: "",
                            existingData?.feedback ?: ""
                        )

                        // Check if document already exists in ReadingTestsAnswers
                        mDataBase.collection("ReadingTestsAnswers")
                            .whereEqualTo("studentId", currentUserID)
                            .whereEqualTo("id", id)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                if (querySnapshot.isEmpty) {
                                    // Document does not exist, create a new one
                                    mDataBase.collection("ReadingTestsAnswers")
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
                                    mDataBase.collection("ReadingTestsAnswers")
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
                        Toast.makeText(this, "Error fetching Reading Test: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error fetching user data: ${e.message}", Toast.LENGTH_SHORT).show()
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