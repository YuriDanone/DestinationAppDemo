package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomatest.adapter.AdminHomeAdapter
import com.example.diplomatest.databinding.ActivityAdminBinding
import com.example.diplomatest.model.DataSource
import com.google.firebase.firestore.*

class AdminActivity: AppCompatActivity() {
    lateinit var mDataBase: FirebaseFirestore
    private lateinit var homeList: ArrayList<DataSource>
    private lateinit var mAdapter: AdminHomeAdapter
    private lateinit var binding: ActivityAdminBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding = ActivityAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        homeList = ArrayList()
        mAdapter = AdminHomeAdapter(this, homeList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mAdapter



        getHomesData()

        binding.newApartmentBtn.setOnClickListener {
            val intent = Intent(this@AdminActivity, AdminAddActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)
        }

        binding.logOutBtn.setOnClickListener{
            val intent = Intent(this@AdminActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getHomesData(){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("Homes").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    val newHome = dc.document.toObject(DataSource::class.java)
                    newHome.id = dc.document.id
                    homeList.add(newHome)
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