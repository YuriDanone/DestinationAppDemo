package com.example.diplomatest

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class RegisterActivity : AppCompatActivity() {

    //private lateinit var tvUsername: TextView
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText

    private lateinit var ivImage: ImageView
    private var uri: Uri? = null


    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Initialize Firebase Auth
        auth = Firebase.auth


        etUsername = findViewById(R.id.editTextRegisterUsername)
        etEmail = findViewById(R.id.editTextRegisterEmail)
        etPhone = findViewById(R.id.editTextRegisterPhone)
        etPassword = findViewById(R.id.editTextRegisterPassword)



        btnRegister = findViewById(R.id.buttonRegisterRegister)

        ivImage = findViewById(R.id.imgAcc)


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

        storage = FirebaseStorage.getInstance()


        btnRegister.setOnClickListener{

            var storageRef = storage.reference.child("Users")

            storageRef = storageRef.child(System.currentTimeMillis().toString())

            if (uri != null){
                storageRef.putFile(uri!!)
                    .addOnSuccessListener { uploadTask ->
                        uploadTask.storage.downloadUrl
                            .addOnSuccessListener{uri ->
                                var rUsername = etUsername.text.toString().trim()
                                var rEmail = etEmail.text.toString().trim()
                                var rPhone = etPhone.text.toString().trim()
                                var rPassword = etPassword.text.toString().trim()
                                var ivImage = uri.toString()


                                auth.createUserWithEmailAndPassword(rEmail, rPassword)
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            //Register in success, update UI with the registered user's information
                                            val user = auth.currentUser
                                            updateUI(user)

                                            if (user != null) {
                                                val newUser = User(user.uid, rUsername, rEmail, rPhone, ivImage)
                                                FirebaseFirestore.getInstance().collection("Users").add(newUser)
                                            }

                                        } else {
                                            // If register in fails, display a message to the user
                                            Toast.makeText(
                                                baseContext, "Authentication failed",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            updateUI(null)
                                        }
                                    }
                            }
                    }
            }


        }
    }



    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}