package com.example.diplomatest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomatest.adapter.HomeAdapter
import com.example.diplomatest.databinding.FragmentCoursesBinding
import com.example.diplomatest.databinding.FragmentHomeBinding
import com.example.diplomatest.model.DataSource
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import java.util.Locale


class CoursesFragment : Fragment() {
    lateinit var mDataBase: FirebaseFirestore
    private lateinit var homeList: ArrayList<DataSource>
    private lateinit var mAdapter: HomeAdapter

    private lateinit var searchView: SearchView




    lateinit var binding: FragmentCoursesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view





    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeList = ArrayList()
        mAdapter = HomeAdapter(requireContext(), homeList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = mAdapter



        getHomesData()

        /*
        searchView = (activity as MainActivity).findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })*/

    }

    private fun filterList(query: String?){
        if (query != null){
            val filteredList = ArrayList<DataSource>()
            for (i in homeList){
                if (i.name!!.lowercase(Locale.ROOT).contains(query) || i.info!!.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
            }else{
                mAdapter.setFilteredList(filteredList)
            }
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