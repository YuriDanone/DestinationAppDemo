package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomatest.adapter.WritingTestResultsAdapter
import com.example.diplomatest.databinding.ActivityWritingResultsBinding
import com.example.diplomatest.model.WritingTest
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class WritingResultsActivity: AppCompatActivity() {
    lateinit var mDataBase: FirebaseFirestore
    private lateinit var writingList: ArrayList<WritingTest>
    private lateinit var mAdapter: WritingTestResultsAdapter
    private lateinit var binding: ActivityWritingResultsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityWritingResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        writingList = ArrayList()
        mAdapter = WritingTestResultsAdapter(this, writingList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mAdapter



        getHomesData()


    }

    private fun getHomesData(){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("WritingTestsAnswers").addSnapshotListener(object :
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
                }
                mAdapter.notifyDataSetChanged()
            }
        })
    }
}