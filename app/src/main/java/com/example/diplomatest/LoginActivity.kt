package com.example.diplomatest

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess
import android.content.res.Configuration
import java.util.*

class LoginActivity: AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnGuest: TextView

    private lateinit var btnSwitch: Switch

    private lateinit var etAdminCode: EditText

    private lateinit var buttonFacebookLogin: Button

    private lateinit var auth: FirebaseAuth

//    // Initialize Facebook Login button
//    val callbackManager = CallbackManager.Factory.create()
//
//    buttonFacebookLogin.setReadPermissions("email", "public_profile")
//    buttonFacebookLogin.registerCallback(
//    callbackManager,
//    object : FacebookCallback<LoginResult> {
//        override fun onSuccess(loginResult: LoginResult) {
//            Log.d(TAG, "facebook:onSuccess:$loginResult")
//            handleFacebookAccessToken(loginResult.accessToken)
//        }
//
//        override fun onCancel() {
//            Log.d(TAG, "facebook:onCancel")
//        }
//
//        override fun onError(error: FacebookException) {
//            Log.d(TAG, "facebook:onError", error)
//        }
//    },
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

//        val currentLanguage = LanguageHelper.getLanguage(this)
//        LanguageHelper.setLanguage(this,currentLanguage)
//        setLanguage(this,"en")

        // Initialize Firebase Auth
        auth = Firebase.auth

        btnLogin = findViewById(R.id.buttonLogin)
        btnRegister = findViewById(R.id.buttonLoginRegister)
        etEmail = findViewById(R.id.editTextLoginEmail)
        etPassword = findViewById(R.id.editTextLoginPassword)

        //btnGuest = findViewById(R.id.guest_btn)

        //btnSwitch = findViewById(R.id.switch_btn)
        //etAdminCode = findViewById(R.id.testEnabeld)

        //etAdminCode.visibility = View.GONE

        /*
        btnSwitch.setOnCheckedChangeListener { _, isCheked ->
            if (btnSwitch.isChecked){
                etAdminCode.visibility = View.VISIBLE
            } else if(!btnSwitch.isChecked){
                etAdminCode.visibility = View.GONE
            }
        }
*/

        btnLogin.setOnClickListener{
            val lEmail = etEmail.text.toString().trim()
            val lPassword = etPassword.text.toString().trim()

            //val lAdminCode = etAdminCode.text.toString().trim()



            auth.signInWithEmailAndPassword(lEmail, lPassword)
                .addOnCompleteListener(this){ task ->
                    if (task.isSuccessful){
                        //Register in success, update UI with the registered user's information
                        val user = auth.currentUser
                        updateUI(user)
                    }
                    /*
                    else if(lEmail == "admin" && lPassword == "admin" && lAdminCode == "admin"){
                        val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                        startActivity(intent)
                    }else if(lEmail == "admin" && lPassword == "admin" && lAdminCode != "admin"){
                        Toast.makeText(
                            baseContext, "Wrong admin code",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    */
                    else {

                        // If register in fails, display a message to the user
                        Toast.makeText(
                            baseContext, "Authentication failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }

        btnRegister.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
/*
        btnGuest.setOnClickListener {
            val intent = Intent(this@LoginActivity, GuestActivity::class.java)
            startActivity(intent)
        }*/
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

//    private fun setLanguage(context: Context, languageCode: String) {
//        val locale = Locale(languageCode)
//        Locale.setDefault(locale)
//
//        val resources = context.resources
//        val configuration = Configuration(resources.configuration)
//        configuration.setLocale(locale)
//
//        resources.updateConfiguration(configuration, resources.displayMetrics)
//    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Pass the activity result back to the Facebook SDK
//        callbackManager.onActivityResult(requestCode, resultCode, data)
//    }
}