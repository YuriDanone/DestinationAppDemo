package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomatest.adapter.ListeningTestAdapter
import com.example.diplomatest.databinding.ActivityListeningBinding
import com.example.diplomatest.model.ListeningTest
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ListeningActivity: AppCompatActivity() {
    lateinit var mDataBase: FirebaseFirestore
    private lateinit var listeningList: ArrayList<ListeningTest>
    private lateinit var mAdapter: ListeningTestAdapter
    private lateinit var binding: ActivityListeningBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityListeningBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        listeningList = ArrayList()
        mAdapter = ListeningTestAdapter(this, listeningList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mAdapter



        getHomesData()



    }

    private fun getHomesData(){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("ListeningTests").addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    val newPart = dc.document.toObject(ListeningTest::class.java)
                    newPart.id = dc.document.id
                    listeningList.add(newPart)
                }
                mAdapter.notifyDataSetChanged()
            }
        })
    }
}