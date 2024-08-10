package com.example.diplomatest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.net.URL

class InfoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
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
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
    }

}