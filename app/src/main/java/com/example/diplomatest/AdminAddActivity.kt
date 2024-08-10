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
import com.example.diplomatest.databinding.ActivityAdminAddBinding
import com.example.diplomatest.model.DataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AdminAddActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddBinding

    private lateinit var ivImage: ImageView
    private var uri: Uri? = null

    private lateinit var etName: EditText
    private lateinit var etCity: EditText
    private lateinit var etAddress: EditText
    private lateinit var etCommisDate: EditText
    private lateinit var etDescription: EditText
    private lateinit var add: Button

    lateinit var mDataBase: FirebaseFirestore
    lateinit var storage: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityAdminAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        storage = FirebaseStorage.getInstance()

        ivImage = findViewById(R.id.image_add)

        etName = findViewById(R.id.editTextName)
        etCity = findViewById(R.id.editTextCity)
        etAddress = findViewById(R.id.editTextAddress)
        etCommisDate = findViewById(R.id.editTextCommisDate)
        etDescription = findViewById(R.id.editTextDescription)

        add = findViewById(R.id.add_apartment_btn)

        var galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            {
                ivImage.setImageURI(it)
                uri = it!!
            }
        )

        ivImage.setOnClickListener{
            galleryImage.launch("image/*")
        }

        add.setOnClickListener {
            addNewApartment()
        }
    }

    fun addNewApartment(){
        var storageRef = storage.reference.child("Homes")

        storageRef = storageRef.child(System.currentTimeMillis().toString())

        if (uri != null) {
            storageRef.putFile(uri!!)
                .addOnSuccessListener { uploadTask ->
                    uploadTask.storage.downloadUrl
                        .addOnSuccessListener { uri ->

                            var downloadUrl = uri.toString()
                            var nName = etName.text.toString().trim()
                            var nCity = etCity.text.toString().trim()
                            var nAddress = etAddress.text.toString().trim()
                            var nCommisDate = etCommisDate.text.toString().trim()
                            var nDescription = etDescription.text.toString().trim()
                            mDataBase = FirebaseFirestore.getInstance()

                            val newDocumentRef = mDataBase.collection("Homes").document()

                            // Get the generated document ID


                            // Create a DataSource object with the generated ID
                            val dataSource = DataSource(
                                "",
                                nName,
                                nAddress,
                                downloadUrl,
                                nCity,
                                nCommisDate,
                                nDescription
                            )

                            // Set the ID field of the DataSource object

                            mDataBase.collection("Homes")
                                .add(dataSource)

                            overridePendingTransition(
                                R.anim.slide_enter_left,
                                R.anim.slide_exit_right
                            )
                            finish()
                            val intent = Intent(this, AdminActivity::class.java)
                            startActivity(intent)
                        }
                }
        }else{

            var downloadUrl = uri.toString()
            var nName = etName.text.toString().trim()
            var nCity = etCity.text.toString().trim()
            var nAddress = etAddress.text.toString().trim()
            var nCommisDate = etCommisDate.text.toString().trim()
            var nDescription = etDescription.text.toString().trim()
            mDataBase = FirebaseFirestore.getInstance()

            val newDocumentRef = mDataBase.collection("Homes").document()

            // Get the generated document ID


            // Create a DataSource object with the generated ID
            val dataSource = DataSource(
                "",
                nName,
                nAddress,
                downloadUrl,
                nCity,
                nCommisDate,
                nDescription
            )

            // Set the ID field of the DataSource object


            mDataBase.collection("Homes")
                .add(dataSource)

            overridePendingTransition(
                R.anim.slide_enter_left,
                R.anim.slide_exit_right
            )
            finish()
            val intent = Intent(this, AdminActivity::class.java)
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
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }
}



