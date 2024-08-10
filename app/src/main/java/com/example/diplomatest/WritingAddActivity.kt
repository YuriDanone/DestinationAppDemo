package com.example.diplomatest

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.databinding.ActivityWritingAddBinding
import com.example.diplomatest.model.WritingTest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class WritingAddActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWritingAddBinding
    private lateinit var ivImage1: ImageView
    //private lateinit var ivImage2: ImageView
    private var uri: Uri? = null


    private lateinit var ivAudio: ImageView
    private var uriAudio: Uri? = null
    private lateinit var etPart: EditText

    private lateinit var add: Button

    lateinit var mDataBase: FirebaseFirestore
    lateinit var storage: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityWritingAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        storage = FirebaseStorage.getInstance()

        ivImage1 = findViewById(R.id.image_add1)
        //ivImage2 = findViewById(R.id.image_add2)

        etPart = findViewById(R.id.editTextPart)


        add = findViewById(R.id.add_apartment_btn)

        var galleryImage1 = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            {
                ivImage1.setImageURI(it)
                uri = it!!
            }
        )


        ivImage1.setOnClickListener{
            galleryImage1.launch("image/*")
        }

//        ivImage2.setOnClickListener {
//            galleryImage2.launch("image/*")
//        }

        add.setOnClickListener {
            addNewTest()
        }
    }

    fun addNewTest(){
        var storageRef = storage.reference.child("WritingTests")

        storageRef = storageRef.child(System.currentTimeMillis().toString())

        if (uri != null) {
            storageRef.putFile(uri!!)
                .addOnSuccessListener { uploadTask ->
                    uploadTask.storage.downloadUrl
                        .addOnSuccessListener { uri ->

                            var downloadUrl1 = uri.toString()
                            //var downloadUrl2 = uri.toString()
                            //var audioUrl = uri.toString()
                            var etPart = etPart.text.toString().trim()
                            mDataBase = FirebaseFirestore.getInstance()

                            // Generate a new document reference
                            val newDocumentRef = mDataBase.collection("WritingTests").document()
                            val documentId = newDocumentRef.id
                            // Get the generated document ID


                            // Create a DataSource object with the generated ID
                            val writingTest = WritingTest(
                                "",
                                "",
                                documentId,
                                etPart,
                                downloadUrl1,
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                            )

                            // Set the ID field of the DataSource object

                            // Add the WritingTest object to Firestore with the generated ID
                            newDocumentRef.set(writingTest)
                                .addOnSuccessListener {
                                    overridePendingTransition(
                                        R.anim.slide_enter_left,
                                        R.anim.slide_exit_right
                                    )
                                    finish()
                                    val intent = Intent(this, WritingActivity::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    // Handle failure
                                    Log.w("AddNewTest", "Error adding document", e)
                                }

                        }
                }
        }else{

            var downloadUrl1 = uri.toString()
            //var downloadUrl2 = uri.toString()
            //var audioUrl = uri.toString()
            var etPart = etPart.text.toString().trim()
            mDataBase = FirebaseFirestore.getInstance()

            // Generate a new document reference
            val newDocumentRef = mDataBase.collection("WritingTests").document()
            val documentId = newDocumentRef.id

            // Get the generated document ID


            // Create a DataSource object with the generated ID
            val writingTest = WritingTest(
                "",
                "",
                documentId,
                etPart,
                downloadUrl1,
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )

            // Set the ID field of the DataSource object


            // Add the WritingTest object to Firestore with the generated ID
            newDocumentRef.set(writingTest)
                .addOnSuccessListener {
                    overridePendingTransition(
                        R.anim.slide_enter_left,
                        R.anim.slide_exit_right
                    )
                    finish()
                    val intent = Intent(this, WritingActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    Log.w("AddNewTest", "Error adding document", e)
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
        val intent = Intent(this, WritingActivity::class.java)
        startActivity(intent)
    }
}