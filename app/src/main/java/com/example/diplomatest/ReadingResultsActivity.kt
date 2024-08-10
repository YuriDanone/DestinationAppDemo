package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomatest.adapter.ReadingTestResultsAdapter
import com.example.diplomatest.databinding.ActivityReadingResultsBinding
import com.example.diplomatest.model.ReadingTest
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ReadingResultsActivity: AppCompatActivity() {
    lateinit var mDataBase: FirebaseFirestore
    private lateinit var readingList: ArrayList<ReadingTest>
    private lateinit var mAdapter: ReadingTestResultsAdapter
    private lateinit var binding: ActivityReadingResultsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityReadingResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        readingList = ArrayList()
        mAdapter = ReadingTestResultsAdapter(this, readingList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mAdapter



        getHomesData()

//        binding.addTestBtn.setOnClickListener {
//            val intent = Intent(this@ReadingResultsActivity, ReadingAddActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)
//        }

    }

    private fun getHomesData(){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("ReadingTestsAnswers").addSnapshotListener(object :
            EventListener<QuerySnapshot> {
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