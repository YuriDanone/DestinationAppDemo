package com.example.diplomatest

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.databinding.ActivityReadingAddBinding
import com.example.diplomatest.model.ReadingTest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ReadingAddActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityReadingAddBinding
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

        binding = ActivityReadingAddBinding.inflate(layoutInflater)
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

        ivImage1.setOnClickListener{
            galleryImage1.launch("image/*")
        }

//        ivImage2.setOnClickListener {
//            galleryImage2.launch("image/*")
//        }

        add.setOnClickListener {
            addNewApartment()
        }
    }

//    private fun addNewApartment() {
//        val start = findViewById<EditText>(R.id.editTextStart).text.toString().toIntOrNull()
//        val end = findViewById<EditText>(R.id.editTextEnd).text.toString().toIntOrNull()
//
//        if (start != null && end != null && start <= end) {
//            fillFields(start, end)
//        } else {
//            Toast.makeText(this, "Invalid range", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun fillFields(start: Int, end: Int) {
//        val mDataBase = FirebaseFirestore.getInstance()
//        val etPart = findViewById<EditText>(R.id.editTextPart).text.toString().trim()
//        val downloadUrl1 = "" // Set your download URL here
//
//        for (i in start..end) {
//            val fieldName = "n$i"
//            val fieldValue = "Value for $fieldName" // You can set this to any value you want
//
//            val listeningTest = ListeningTest(
//                "", // Update with appropriate value
//                "", // Update with appropriate value
//                etPart,
//                "", // Update with appropriate value
//                downloadUrl1,
//                "", // Update with appropriate value
//                "", // Update with appropriate value
//                "", // Update with appropriate value
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                if (i == start) fieldValue else "",
//                if (i == start + 1) fieldValue else "",
//                if (i == start + 2) fieldValue else "",
//                if (i == start + 3) fieldValue else "",
//                if (i == start + 4) fieldValue else "",
//                if (i == start + 5) fieldValue else "",
//                if (i == start + 6) fieldValue else "",
//                if (i == start + 7) fieldValue else "",
//                if (i == start + 8) fieldValue else "",
//                if (i == start + 9) fieldValue else "",
//                if (i == start + 10) fieldValue else "",
//                if (i == start + 11) fieldValue else "",
//                if (i == start + 12) fieldValue else "",
//                if (i == start + 13) fieldValue else ""
//            )
//
//            // Add the listeningTest object to Firestore or perform any other desired operation
//            mDataBase.collection("ListeningTests").add(listeningTest)
//                .addOnSuccessListener {
//                    // Handle success
//                }
//                .addOnFailureListener { e ->
//                    // Handle failure
//                }
//        }
//    }

    fun addNewApartment(){
        var storageRef = storage.reference.child("ReadingTests")

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

                            val newDocumentRef = mDataBase.collection("ReadingTests").document()

                            // Get the generated document ID


                            // Create a DataSource object with the generated ID
                            val readingTest = ReadingTest(
                                "",
                                "",
                                "",
                                etPart,
                                downloadUrl1,
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                            )

                            // Set the ID field of the DataSource object

                            mDataBase.collection("ReadingTests")
                                .add(readingTest)

                            overridePendingTransition(
                                R.anim.slide_enter_left,
                                R.anim.slide_exit_right
                            )
                            finish()
                            val intent = Intent(this, ReadingActivity::class.java)
                            startActivity(intent)
                        }
                }
        }else{

            var downloadUrl1 = uri.toString()
            //var downloadUrl2 = uri.toString()
            //var audioUrl = uri.toString()
            var etPart = etPart.text.toString().trim()
            mDataBase = FirebaseFirestore.getInstance()

            val newDocumentRef = mDataBase.collection("ReadingTests").document()

            // Get the generated document ID


            // Create a DataSource object with the generated ID
            val readingTest = ReadingTest(
                "",
                "",
                "",
                etPart,
                downloadUrl1,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )

            // Set the ID field of the DataSource object


            mDataBase.collection("ReadingTests")
                .add(readingTest)

            overridePendingTransition(
                R.anim.slide_enter_left,
                R.anim.slide_exit_right
            )
            finish()
            val intent = Intent(this, ReadingActivity::class.java)
            startActivity(intent)
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
        val intent = Intent(this, ReadingActivity::class.java)
        startActivity(intent)
    }
}