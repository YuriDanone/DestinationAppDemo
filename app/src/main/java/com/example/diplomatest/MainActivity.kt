package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.diplomatest.databinding.ActivityMainBinding
import com.example.diplomatest.databinding.NavHeaderBinding
import com.example.diplomatest.model.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private var userListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        auth = Firebase.auth
        val userId = auth.currentUser?.uid

        if (userId != null) {
            listenForUserUpdates(userId)
        }

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.courses -> replaceFragment(CoursesFragment())
                R.id.results -> replaceFragment(ResultsFragment())
                else -> {}
            }
            true
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                //R.id.nav_progress -> Toast.makeText(applicationContext, "Clicked Progress", Toast.LENGTH_SHORT).show()
                //R.id.nav_attendance -> Toast.makeText(applicationContext, "Clicked Attendance", Toast.LENGTH_SHORT).show()
                R.id.nav_dashboard -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)
                }
                //R.id.nav_settings -> Toast.makeText(applicationContext, "Clicked Settings", Toast.LENGTH_SHORT).show()
                //R.id.nav_connect -> Toast.makeText(applicationContext, "Clicked Connect", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun listenForUserUpdates(userId: String) {
        val firestore = FirebaseFirestore.getInstance()
        val userRef = firestore.collection("Users").whereEqualTo("userId", userId)

        userListener = userRef.addSnapshotListener { snapshots, e ->
            if (e != null) {
                Toast.makeText(this, "Error listening for user updates: ${e.message}", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            if (snapshots != null && !snapshots.isEmpty) {
                val document = snapshots.documents[0]
                val user = document.toObject(User::class.java)
                user?.let {
                    updateNavHeader(it)
                }
            }
        }
    }

    private fun updateNavHeader(user: User) {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val navHeaderBinding = NavHeaderBinding.bind(headerView)

        navHeaderBinding.userName.text = user.username
        navHeaderBinding.userMail.text = user.email
        Glide.with(this).load(user.imageUrl).into(navHeaderBinding.userImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        userListener?.remove()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
