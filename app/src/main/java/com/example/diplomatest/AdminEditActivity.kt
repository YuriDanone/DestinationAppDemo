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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.diplomatest.databinding.ActivityAdminAddBinding
import com.example.diplomatest.databinding.ActivityAdminEditBinding
import com.example.diplomatest.model.DataSource
import com.example.diplomatest.ui.theme.DiplomaTestTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AdminEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminEditBinding

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
        binding = ActivityAdminEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.extras?.getString("id") ?: error("not found")

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
            editApartment(id)
        }

    }

    fun editApartment(id: String) {
        var storageRef = storage.reference.child("Homes")

        storageRef = storageRef.child(id) // Assuming apartmentId is the ID of the apartment you want to edit

        if (uri != null) {
            storageRef.putFile(uri!!)
                .addOnSuccessListener { uploadTask ->
                    uploadTask.storage.downloadUrl
                        .addOnSuccessListener { uri ->
                            val downloadUrl = uri.toString()
                            val nName = etName.text.toString().trim()
                            val nCity = etCity.text.toString().trim()
                            val nAddress = etAddress.text.toString().trim()
                            val nCommisDate = etCommisDate.text.toString().trim()
                            val nDescription = etDescription.text.toString().trim()
                            mDataBase = FirebaseFirestore.getInstance()

                            // Fetch existing data from Firestore
                            mDataBase.collection("Homes")
                                .document(id)
                                .get()
                                .addOnSuccessListener { documentSnapshot ->
                                    val existingData = documentSnapshot.toObject(DataSource::class.java)

                                    // Check if fields are empty, if so, retain existing data
                                    val newData = DataSource(
                                        id,
                                        if (nName.isNotEmpty()) nName else existingData?.name ?: "",
                                        if (nAddress.isNotEmpty()) nAddress else existingData?.info ?: "",
                                        downloadUrl,
                                        if (nCity.isNotEmpty()) nCity else existingData?.city ?: "",
                                        if (nCommisDate.isNotEmpty()) nCommisDate else existingData?.commis_date ?: "",
                                        if (nDescription.isNotEmpty()) nDescription else existingData?.description ?: ""
                                    )

                                    // Update the document with new or existing data
                                    mDataBase.collection("Homes")
                                        .document(id)
                                        .set(newData)
                                        .addOnSuccessListener {
                                            overridePendingTransition(
                                                R.anim.slide_enter_left,
                                                R.anim.slide_exit_right
                                            )
                                            finish()
                                            val intent = Intent(this, AdminActivity::class.java)
                                            startActivity(intent)
                                        }
                                }
                        }
                }
        } else {
            // Handle case when URI is null (no new image selected)
            val nName = etName.text.toString().trim()
            val nCity = etCity.text.toString().trim()
            val nAddress = etAddress.text.toString().trim()
            val nCommisDate = etCommisDate.text.toString().trim()
            val nDescription = etDescription.text.toString().trim()
            mDataBase = FirebaseFirestore.getInstance()

            // Fetch existing data from Firestore
            mDataBase.collection("Homes")
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val existingData = documentSnapshot.toObject(DataSource::class.java)

                    // Check if fields are empty, if so, retain existing data
                    val newData = DataSource(
                        id,
                        if (nName.isNotEmpty()) nName else existingData?.name ?: "",
                        if (nAddress.isNotEmpty()) nAddress else existingData?.info ?: "",
                        existingData?.img ?: "",
                        if (nCity.isNotEmpty()) nCity else existingData?.city ?: "",
                        if (nCommisDate.isNotEmpty()) nCommisDate else existingData?.commis_date ?: "",
                        if (nDescription.isNotEmpty()) nDescription else existingData?.description ?: ""
                    )

                    // Update the document with new or existing data
                    mDataBase.collection("Homes")
                        .document(id)
                        .set(newData)
                        .addOnSuccessListener {
                            overridePendingTransition(
                                R.anim.slide_enter_left,
                                R.anim.slide_exit_right
                            )
                            finish()
                            val intent = Intent(this, AdminActivity::class.java)
                            startActivity(intent)
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }

}
