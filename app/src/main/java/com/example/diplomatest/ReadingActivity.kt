package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomatest.adapter.ReadingTestAdapter
import com.example.diplomatest.databinding.ActivityReadingBinding
import com.example.diplomatest.model.ReadingTest
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ReadingActivity: AppCompatActivity() {
    lateinit var mDataBase: FirebaseFirestore
    private lateinit var readingList: ArrayList<ReadingTest>
    private lateinit var mAdapter: ReadingTestAdapter
    private lateinit var binding: ActivityReadingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityReadingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        readingList = ArrayList()
        mAdapter = ReadingTestAdapter(this, readingList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mAdapter



        getHomesData()

        binding.addTestBtn.setOnClickListener {
            val intent = Intent(this@ReadingActivity, ReadingAddActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)
        }

    }

    private fun getHomesData(){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("ReadingTests").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    val newPart = dc.document.toObject(ReadingTest::class.java)
                    newPart.id = dc.document.id
                    readingList.add(newPart)
                    //homeList.add(dc.document.toObject(DataSource::class.java))
                    //userArrayList.reverse()
//                    if (dc.type == DocumentChange.Type.MODIFIED){
//                        homeList.clear()
//                        getHomesData()
//                    }
//                    if (dc.type == DocumentChange.Type.ADDED){
//                        homeList.add(dc.document.toObject(DataSource::class.java))
//                    }

                    //homeList.addAll(homeList)
                }
                mAdapter.notifyDataSetChanged()
            }
        })
    }

}


/*
private lateinit var runnable: Runnable
    private var handler = Handler()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening)

        val seekbar: SeekBar = findViewById(R.id.seekbar)
        val playButton: ImageView = findViewById(R.id.ivPlay)

        val mediaPlayer = MediaPlayer.create(this, R.raw.ielts1)



        val seekbar2: SeekBar = findViewById(R.id.seekbar2)
        val playButton2: ImageView = findViewById(R.id.ivPlay2)

        val mediaPlayer2 = MediaPlayer.create(this, R.raw.ielts2)

        if (mediaPlayer != null){

            seekbar.progress = 0
            seekbar.max = mediaPlayer.duration

            playButton.setOnClickListener {
                if (!mediaPlayer.isPlaying){
                    mediaPlayer.start()
                    //playButton.setBackgroundResource(R.drawable.)
                } else{
                    mediaPlayer.pause()
                }
            }


            seekbar2.progress = 0
            seekbar2.max = mediaPlayer2.duration

            playButton2.setOnClickListener {
                if (!mediaPlayer2.isPlaying){
                    mediaPlayer2.start()
                    //playButton.setBackgroundResource(R.drawable.)
                } else{
                    mediaPlayer2.pause()
                }
            }

            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2){
                        mediaPlayer.seekTo(p1)
                        mediaPlayer2.seekTo(p1)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })

            runnable = Runnable {
                seekbar.progress = mediaPlayer.currentPosition
                handler.postDelayed(runnable, 1000)
            }

            handler.postDelayed(runnable, 1000)
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.pause()
            }

        } else {
            Log.d("qwerty", "qwerty")

        }


    }
 */

/*
<?xml version="1.0" encoding="utf-8"?>
<androidx.core.widget.NestedScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ListeningActivity">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:id="@+id/tvListening"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Listening"
            android:textSize="30dp"
            android:textColor="@color/black"
            android:textStyle="bold"
            android:paddingStart="25dp"
            android:paddingTop="15dp"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"/>

        <TextView
            android:id="@+id/tvSection1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Section 1"
            android:textSize="25dp"
            android:textColor="@color/black"
            android:textStyle="bold"
            android:paddingStart="25dp"
            android:paddingTop="15dp"
            app:layout_constraintTop_toBottomOf="@id/tvListening"
            app:layout_constraintStart_toStartOf="parent"/>

        <LinearLayout
            android:id="@+id/sbAudio"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tvSection1"
            android:layout_marginTop="8dp"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="35dp"
                android:layout_height="35dp"
                android:background="@drawable/img_2"
                android:layout_marginStart="25dp"
                android:layout_marginEnd="10dp"/>

            <SeekBar
                android:id="@+id/seekbar"
                android:layout_width="285dp"
                android:layout_height="wrap_content"
                app:layout_constraintTop_toBottomOf="@id/tvSection1"
                app:layout_constraintStart_toStartOf="parent"
                android:layout_gravity="center"/>

            <ImageView
                android:id="@+id/ivPlay"
                android:layout_width="35dp"
                android:layout_height="35dp"
                android:background="@drawable/img_3"/>

        </LinearLayout>

        <ImageView
            android:id="@+id/part1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/part1_1"
            android:scaleType="centerCrop"
            app:layout_constraintTop_toBottomOf="@id/sbAudio"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toTopOf="@id/lLeT1"/>





        <LinearLayout
            android:id="@+id/lLeT1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintTop_toBottomOf="@id/part1"
            app:layout_constraintStart_toStartOf="parent"
            android:orientation="vertical">



            <TextView
                android:id="@+id/tv1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="1."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="8dp"
                android:layout_marginStart="25dp"/>

            <EditText
                android:id="@+id/et1"
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="55dp"
                android:background="@drawable/bg"/>

            <TextView
                android:id="@+id/tv2"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="2."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>

            <EditText
                android:id="@+id/et2"
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="55dp"
                android:background="@drawable/bg"/>

            <TextView
                android:id="@+id/tv3"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="3."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:id="@+id/et3"
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="55dp"
                android:background="@drawable/bg"/>
            <TextView
                android:id="@+id/tv4"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="4."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:id="@+id/et4"
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="55dp"
                android:background="@drawable/bg"/>
            <TextView
                android:id="@+id/tv5"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="5."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:id="@+id/et5"
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="55dp"
                android:background="@drawable/bg"/>
            <TextView
                android:id="@+id/tv6"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="6."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:id="@+id/et6"
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="55dp"
                android:background="@drawable/bg"/>
        </LinearLayout>





        <ImageView
            android:id="@+id/part2"
            android:layout_width="match_parent"
            android:layout_height="350dp"
            android:background="@drawable/part1_2"
            app:layout_constraintTop_toBottomOf="@id/lLeT1"/>

        <LinearLayout
            android:id="@+id/lLeT2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintTop_toBottomOf="@id/part2"
            app:layout_constraintStart_toStartOf="parent"
            android:orientation="vertical">



            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="6."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="8dp"
                android:layout_marginStart="25dp"/>

            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="7."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>

            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="8."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="9."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="10."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

        </LinearLayout>


        <LinearLayout
            android:id="@+id/sbAudio2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/lLeT2"
            android:layout_marginTop="8dp"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="35dp"
                android:layout_height="35dp"
                android:background="@drawable/img_2"
                android:layout_marginStart="25dp"
                android:layout_marginEnd="10dp"/>

            <SeekBar
                android:id="@+id/seekbar2"
                android:layout_width="285dp"
                android:layout_height="wrap_content"
                app:layout_constraintTop_toBottomOf="@id/lLeT2"
                app:layout_constraintStart_toStartOf="parent"
                android:layout_gravity="center"/>

            <ImageView
                android:id="@+id/ivPlay2"
                android:layout_width="35dp"
                android:layout_height="35dp"
                android:background="@drawable/img_3"/>

        </LinearLayout>


        <ImageView
            android:id="@+id/part3"
            android:layout_width="match_parent"
            android:layout_height="350dp"
            android:background="@drawable/part2_1"
            app:layout_constraintTop_toBottomOf="@id/sbAudio2"/>


        <LinearLayout
            android:id="@+id/lLeT3"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintTop_toBottomOf="@id/part3"
            app:layout_constraintStart_toStartOf="parent"
            android:orientation="vertical">



            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="11."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="8dp"
                android:layout_marginStart="25dp"/>

            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="12."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>

            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="13."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="14."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

        </LinearLayout>

        <ImageView
            android:id="@+id/part4"
            android:layout_width="match_parent"
            android:layout_height="350dp"
            android:background="@drawable/part2_2"
            app:layout_constraintTop_toBottomOf="@id/lLeT3"/>


        <LinearLayout
            android:id="@+id/lLeT4"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintTop_toBottomOf="@id/part4"
            app:layout_constraintStart_toStartOf="parent"
            android:orientation="vertical">



            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="15."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="8dp"
                android:layout_marginStart="25dp"/>

            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="16."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>

            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="17."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="18."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="19."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="20."
                android:textColor="@color/black"
                android:textSize="30dp"
                android:layout_marginTop="5dp"
                android:layout_marginStart="25dp"/>
            <EditText
                android:layout_width="100dp"
                android:layout_height="30dp"
                android:layout_marginTop="-34dp"
                android:layout_marginStart="70dp"
                android:background="@drawable/bg"/>

        </LinearLayout>

    </androidx.constraintlayout.widget.ConstraintLayout>

</androidx.core.widget.NestedScrollView>
 */