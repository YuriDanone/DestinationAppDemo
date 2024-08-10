package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.adapter.HomeAdapter
import com.example.diplomatest.databinding.ActivityAdminEditBinding
import com.example.diplomatest.databinding.ActivityAdminInfoBinding
import com.example.diplomatest.model.DataSource
import com.google.firebase.firestore.*
import com.squareup.picasso.Picasso

class AdminInfoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAdminInfoBinding

    lateinit var mDataBase: FirebaseFirestore
    private lateinit var homeList: ArrayList<DataSource>
    private lateinit var mAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityAdminInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val homeId = intent.extras?.get("id").toString()

        val img = intent.extras?.get("img").toString()
        if (img.isEmpty()){
            findViewById<ImageView>(R.id.info_img).setImageResource(R.drawable.img_7)
        }else{
            Picasso.get()
                .load(img)
                .into(findViewById<ImageView>(R.id.info_img))}
        val name = intent.extras?.get("name").toString()
        val city = intent.extras?.get("city").toString()
        val commis_date = intent.extras?.get("commis_date").toString()
        val description = intent.extras?.get("description").toString()
        findViewById<ImageView>(R.id.info_img)
        findViewById<TextView>(R.id.info_name).setText(name)
        findViewById<TextView>(R.id.info_city).setText(city)
        findViewById<TextView>(R.id.info_commis).setText(commis_date)
        findViewById<TextView>(R.id.info_description).setText(description)

//        val name = intent.extras?.get("id").toString()
//        findViewById<TextView>(R.id.info_name)
//
//        Picasso.get()
//            .load(name)
//            .into(findViewById<TextView>(R.id.info_name))
        findViewById<Button>(R.id.delete_apartment_btn).setOnClickListener {
            deleteBtn(homeId)
        }

        binding.editApartmentBtn.setOnClickListener {
            editBtn(homeId)
        }
    }



    fun deleteBtn(homeId: String){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("Homes").document(homeId).delete()
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)

    }

    fun editBtn(homeId: String){
        val intent = Intent(this, AdminEditActivity::class.java)
        intent.putExtra("id", homeId)
        startActivity(intent)
    }

//    override fun finish() {
//        super.finish()
//        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }

}