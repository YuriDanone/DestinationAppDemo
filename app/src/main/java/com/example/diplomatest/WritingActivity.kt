package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomatest.adapter.WritingTestAdapter
import com.example.diplomatest.databinding.ActivityWritingBinding
import com.example.diplomatest.model.WritingTest
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class WritingActivity: AppCompatActivity() {
    lateinit var mDataBase: FirebaseFirestore
    private lateinit var writingList: ArrayList<WritingTest>
    private lateinit var mAdapter: WritingTestAdapter
    private lateinit var binding: ActivityWritingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityWritingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        writingList = ArrayList()
        mAdapter = WritingTestAdapter(this, writingList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mAdapter



        getHomesData()

        binding.addTestBtn.setOnClickListener {
            val intent = Intent(this@WritingActivity, WritingAddActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)
        }

    }

    private fun getHomesData(){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("WritingTests").addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    val newPart = dc.document.toObject(WritingTest::class.java)
                    newPart.id = dc.document.id
                    writingList.add(newPart)
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