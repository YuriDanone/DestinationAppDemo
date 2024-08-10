package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.adapter.WritingTestResultsAdapter
import com.example.diplomatest.databinding.ActivityWritingInfoResultsBinding
import com.example.diplomatest.model.WritingTest
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class WritingInfoResultsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWritingInfoResultsBinding

    lateinit var mDataBase: FirebaseFirestore
    private lateinit var writingList: ArrayList<WritingTest>
    private lateinit var mAdapter: WritingTestResultsAdapter

    private lateinit var runnable: Runnable
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityWritingInfoResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //val seekbar: SeekBar = findViewById(R.id.seekbar)
        //val playButton: ImageView = findViewById(R.id.ivPlay)

        val id = intent.extras?.get("id").toString()
        val img = intent.extras?.get("img1").toString()
        val imageView = findViewById<AspectRatioImageView>(R.id.part_img1)

        if (img.isEmpty()) {
            imageView.setImageResource(R.drawable.img_7)
        } else {
            Picasso.get()
                .load(img)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        val aspectRatio = imageView.drawable.intrinsicWidth.toFloat() / imageView.drawable.intrinsicHeight.toFloat()
                        imageView.setAspectRatio(aspectRatio)
                    }

                    override fun onError(e: Exception?) {
                        // Handle error
                    }
                })
        }

        val img2 = intent.extras?.get("img2").toString()
        val imageView2 = findViewById<AspectRatioImageView>(R.id.part_img2)

        if (img2.isEmpty()) {
            imageView2.setImageResource(R.drawable.img_7)
        } else {
            Picasso.get()
                .load(img2)
                .into(imageView2, object : Callback {
                    override fun onSuccess() {
                        val aspectRatio = imageView2.drawable.intrinsicWidth.toFloat() / imageView2.drawable.intrinsicHeight.toFloat()
                        imageView2.setAspectRatio(aspectRatio)
                    }

                    override fun onError(e: Exception?) {
                        // Handle error
                    }
                })
        }


        val q1 = intent.extras?.get("q1").toString()
        val q2 = intent.extras?.get("q2").toString()
        val wordCount1 = intent.extras?.get("wordCount1").toString()
        val wordCount2 = intent.extras?.get("wordCount2").toString()
        val feedback1 = intent.extras?.get("feedback1").toString()
        val feedback2 = intent.extras?.get("feedback2").toString()


        binding.et1.setText(q1)
        findViewById<TextView>(R.id.et2).setText(q2)
        findViewById<TextView>(R.id.wordCount1).setText(wordCount1)
        findViewById<TextView>(R.id.wordCount2).setText(wordCount2)
        binding.feedback1.setText(feedback1)
        binding.feedback2.setText(feedback2)



        findViewById<ImageView>(R.id.part_img1)

        //findViewById<ImageView>(R.id.part_img2)


//        val name = intent.extras?.get("id").toString()
//        findViewById<TextView>(R.id.info_name)
//
//        Picasso.get()
//            .load(name)
//            .into(findViewById<TextView>(R.id.info_name))
        findViewById<Button>(R.id.fill_answers_btn).setOnClickListener {
            editBtn(id)
            //deleteBtn(id)
        }

//        binding.editApartmentBtn.setOnClickListener {
//            editBtn(id)
//        }
    }




    fun deleteBtn(id: String){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("WritingTestsAnswers").document(id).delete()
        val intent = Intent(this, WritingActivity::class.java)
        startActivity(intent)

    }

    fun editBtn(id: String){
        val intent = Intent(this, WritingEditResultsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}