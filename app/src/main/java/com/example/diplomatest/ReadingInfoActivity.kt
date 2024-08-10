package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.adapter.ReadingTestAdapter
import com.example.diplomatest.databinding.ActivityReadingInfoBinding
import com.example.diplomatest.model.ReadingTest
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ReadingInfoActivity: AppCompatActivity() {
    private lateinit var binding: ActivityReadingInfoBinding

    lateinit var mDataBase: FirebaseFirestore
    private lateinit var readingList: ArrayList<ReadingTest>
    private lateinit var mAdapter: ReadingTestAdapter

    private lateinit var runnable: Runnable
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityReadingInfoBinding.inflate(layoutInflater)
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

        val img3 = intent.extras?.get("img3").toString()
        val imageView3 = findViewById<AspectRatioImageView>(R.id.part_img3)

        if (img3.isEmpty()) {
            imageView3.setImageResource(R.drawable.img_7)
        } else {
            Picasso.get()
                .load(img3)
                .into(imageView3, object : Callback {
                    override fun onSuccess() {
                        val aspectRatio = imageView3.drawable.intrinsicWidth.toFloat() / imageView3.drawable.intrinsicHeight.toFloat()
                        imageView3.setAspectRatio(aspectRatio)
                    }

                    override fun onError(e: Exception?) {
                        // Handle error
                    }
                })
        }
//        val img2 = intent.extras?.get("img2").toString()
//        if (img2.isEmpty()){
//            findViewById<ImageView>(R.id.part_img2).setImageResource(R.drawable.img_7)
//        }else{
//            Picasso.get()
//                .load(img)
//                .into(findViewById<ImageView>(R.id.part_img2))}
        val q1 = intent.extras?.get("q1").toString()
        val q2 = intent.extras?.get("q2").toString()
        val q3 = intent.extras?.get("q3").toString()
        val q4 = intent.extras?.get("q4").toString()
        val q5 = intent.extras?.get("q5").toString()
        val q6 = intent.extras?.get("q6").toString()
        val q7 = intent.extras?.get("q7").toString()
        val q8 = intent.extras?.get("q8").toString()
        val q9 = intent.extras?.get("q9").toString()
        val q10 = intent.extras?.get("q10").toString()
        val q11 = intent.extras?.get("q11").toString()
        val q12 = intent.extras?.get("q12").toString()
        val q13 = intent.extras?.get("q13").toString()
        val q14 = intent.extras?.get("q14").toString()
        val q15 = intent.extras?.get("q15").toString()
        val q16 = intent.extras?.get("q16").toString()
        val q17 = intent.extras?.get("q17").toString()
        val q18 = intent.extras?.get("q18").toString()
        val q19 = intent.extras?.get("q19").toString()
        val q20 = intent.extras?.get("q20").toString()
        val q21 = intent.extras?.get("q21").toString()
        val q22 = intent.extras?.get("q22").toString()
        val q23 = intent.extras?.get("q23").toString()
        val q24 = intent.extras?.get("q24").toString()
        val q25 = intent.extras?.get("q25").toString()
        val q26 = intent.extras?.get("q26").toString()
        val q27 = intent.extras?.get("q27").toString()
        val q28 = intent.extras?.get("q28").toString()
        val q29 = intent.extras?.get("q29").toString()
        val q30 = intent.extras?.get("q30").toString()
        val q31 = intent.extras?.get("q31").toString()
        val q32 = intent.extras?.get("q32").toString()
        val q33 = intent.extras?.get("q33").toString()
        val q34 = intent.extras?.get("q34").toString()
        val q35 = intent.extras?.get("q35").toString()
        val q36 = intent.extras?.get("q36").toString()
        val q37 = intent.extras?.get("q37").toString()
        val q38 = intent.extras?.get("q38").toString()
        val q39 = intent.extras?.get("q39").toString()
        val q40 = intent.extras?.get("q40").toString()

//        binding.et1.setText(q1)
//        findViewById<TextView>(R.id.et2).setText(q2)
//        findViewById<TextView>(R.id.et3).setText(q3)
//        findViewById<TextView>(R.id.et4).setText(q4)
//        findViewById<TextView>(R.id.et5).setText(q5)
//        findViewById<TextView>(R.id.et6).setText(q6)
//        findViewById<TextView>(R.id.et7).setText(q7)
//        findViewById<TextView>(R.id.et8).setText(q8)
//        findViewById<TextView>(R.id.et9).setText(q9)
//        findViewById<TextView>(R.id.et10).setText(q10)
//        findViewById<TextView>(R.id.et11).setText(q11)
//        findViewById<TextView>(R.id.et12).setText(q12)
//        findViewById<TextView>(R.id.et13).setText(q13)
//        findViewById<TextView>(R.id.et14).setText(q14)
//        findViewById<TextView>(R.id.et15).setText(q15)
//        findViewById<TextView>(R.id.et16).setText(q16)
//        findViewById<TextView>(R.id.et17).setText(q17)
//        findViewById<TextView>(R.id.et18).setText(q18)
//        findViewById<TextView>(R.id.et19).setText(q19)
//        findViewById<TextView>(R.id.et20).setText(q20)
//        findViewById<TextView>(R.id.et21).setText(q21)
//        findViewById<TextView>(R.id.et22).setText(q22)
//        findViewById<TextView>(R.id.et23).setText(q23)
//        findViewById<TextView>(R.id.et24).setText(q24)
//        findViewById<TextView>(R.id.et25).setText(q25)
//        findViewById<TextView>(R.id.et26).setText(q26)
//        findViewById<TextView>(R.id.et27).setText(q27)
//        findViewById<TextView>(R.id.et28).setText(q28)
//        findViewById<TextView>(R.id.et29).setText(q29)
//        findViewById<TextView>(R.id.et30).setText(q30)
//        findViewById<TextView>(R.id.et31).setText(q31)
//        findViewById<TextView>(R.id.et32).setText(q32)
//        findViewById<TextView>(R.id.et33).setText(q33)
//        findViewById<TextView>(R.id.et34).setText(q34)
//        findViewById<TextView>(R.id.et35).setText(q35)
//        findViewById<TextView>(R.id.et36).setText(q36)
//        findViewById<TextView>(R.id.et37).setText(q37)
//        findViewById<TextView>(R.id.et38).setText(q38)
//        findViewById<TextView>(R.id.et39).setText(q39)
//        findViewById<TextView>(R.id.et40).setText(q40)

//        val audioUrl = intent.extras?.get("audioUrl").toString()
//        if (audioUrl.isEmpty()){
//            val mediaPlayer = MediaPlayer.create(this, R.raw.ielts1)
//            if (mediaPlayer != null){
//
//                seekbar.progress = 0
//                seekbar.max = mediaPlayer.duration
//
//                playButton.setOnClickListener {
//                    if (!mediaPlayer.isPlaying){
//                        mediaPlayer.start()
//                        //playButton.setBackgroundResource(R.drawable.)
//                    } else{
//                        mediaPlayer.pause()
//                    }
//                }
//                seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                        if (p2){
//                            mediaPlayer.seekTo(p1)
//                        }
//                    }
//
//                    override fun onStartTrackingTouch(p0: SeekBar?) {
//
//                    }
//
//                    override fun onStopTrackingTouch(p0: SeekBar?) {
//
//                    }
//
//                })
//
//                runnable = Runnable {
//                    seekbar.progress = mediaPlayer.currentPosition
//                    handler.postDelayed(runnable, 1000)
//                }
//
//                handler.postDelayed(runnable, 1000)
//                mediaPlayer.setOnCompletionListener {
//                    mediaPlayer.pause()
//                }
//            } else {
//                Log.d("qwerty", "qwerty")
//            }
//        }else {
//            val mediaPlayer = MediaPlayer().apply {
//                setDataSource(audioUrl)
//                prepareAsync()
//                setOnPreparedListener { mp ->
//                    seekbar.progress = 0
//                    seekbar.max = mp.duration
//
//                    playButton.setOnClickListener {
//                        if (!mp.isPlaying) {
//                            mp.start()
//                        } else {
//                            mp.pause()
//                        }
//                    }
//
//                    seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                            if (fromUser) {
//                                mp.seekTo(progress)
//                            }
//                        }
//
//                        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//
//                        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//                    })
//
//                    runnable = Runnable {
//                        seekbar.progress = mp.currentPosition
//                        handler.postDelayed(runnable, 1000)
//                    }
//
//                    handler.postDelayed(runnable, 1000)
//
//                    mp.setOnCompletionListener {
//                        mp.pause()
//                    }
//                }
//                setOnErrorListener { _, _, _ ->
//                    Log.e("MediaPlayer", "Error occurred while playing audio")
//                    false
//                }
//
//            }
//        }

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
        mDataBase.collection("ReadingTests").document(id).delete()
        val intent = Intent(this, ReadingActivity::class.java)
        startActivity(intent)

    }

    fun editBtn(id: String){
        val intent = Intent(this, ReadingEditActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

//    override fun finish() {
//        super.finish()
//        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}