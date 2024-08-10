package com.example.diplomatest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomatest.adapter.HomeAdapter
import com.example.diplomatest.adapter.ListeningTestAdapter
import com.example.diplomatest.adapter.ReadingTestAdapter
import com.example.diplomatest.adapter.TestAdapter
import com.example.diplomatest.adapter.WritingTestAdapter
import com.example.diplomatest.databinding.FragmentHomeBinding
import com.example.diplomatest.model.DataSource
import com.example.diplomatest.model.ListeningTest
import com.example.diplomatest.model.ReadingTest
import com.example.diplomatest.model.Test
import com.example.diplomatest.model.WritingTest
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class HomeFragment : Fragment() {

    lateinit var mDataBase: FirebaseFirestore
    private lateinit var homeList: ArrayList<WritingTest>
    private lateinit var lList: ArrayList<ListeningTest>
    private lateinit var rList: ArrayList<ReadingTest>
    private lateinit var mAdapter: WritingTestAdapter
    private lateinit var lAdapter: ListeningTestAdapter
    private lateinit var rAdapter: ReadingTestAdapter

    //private lateinit var adapter : TestAdapter
    private lateinit var recyclerView: RecyclerView
    //private lateinit var testArrayList: ArrayList<Test>

//    lateinit var imageId: Array<Int>
//    lateinit var heading: Array<String>
//    lateinit var test: ArrayList<String>

    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lList = ArrayList()
        lAdapter = ListeningTestAdapter(requireContext(), lList)
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHome.adapter = lAdapter

        rList = ArrayList()
        rAdapter = ReadingTestAdapter(requireContext(), rList)
        binding.recyclerViewHome2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHome2.adapter = rAdapter

        homeList = ArrayList()
        mAdapter = WritingTestAdapter(requireContext(), homeList)
        binding.recyclerViewHome3.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHome3.adapter = mAdapter


//        binding.welcomeCard.setOnClickListener {
//            val intent = Intent(requireContext(), ProfileActivity::class.java)
//            startActivity(intent)
//        }

//
//        // Ensure EditText is scrollable
//        binding.largeEditText.movementMethod = ScrollingMovementMethod()
//        binding.largeEditText.isVerticalScrollBarEnabled = true
//
//        // Set the EditText to be focusable and scrollable
//        binding.largeEditText.setOnTouchListener { v, event ->
//            v.parent.requestDisallowInterceptTouchEvent(true)
//            when (event.action and MotionEvent.ACTION_MASK) {
//                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
//            }
//            false
//        }
//
//        // Make text selectable
//        binding.largeEditText.setTextIsSelectable(true)
//
//
//        binding.largeEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // No implementation needed
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                // Check if text is empty
//                if (s.isNullOrEmpty()) {
//                    binding.wordCountTextView.text = "Word count: 0"
//                } else {
//                    // Update the word count whenever text changes
//                    val wordCount = s.trim().split("\\s+".toRegex()).size
//                    binding.wordCountTextView.text = "Word count: $wordCount"
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                // No implementation needed
//            }
//        })

        getListeningData()
        getReadingData()
        getHomesData()




        //dataInitialize()



        //adapter = TestAdapter(requireContext(),testArrayList)
//        binding.recyclerViewHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//        binding.recyclerViewHome.setHasFixedSize(true)
//        binding.recyclerViewHome.adapter = adapter


//        binding.recyclerViewHome2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//        binding.recyclerViewHome2.setHasFixedSize(true)
//        binding.recyclerViewHome2.adapter = adapter
//
//
//        binding.recyclerViewHome3.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//        binding.recyclerViewHome3.setHasFixedSize(true)
//        binding.recyclerViewHome3.adapter = adapter


    }


//    private fun dataInitialize(){
//        testArrayList = arrayListOf<Test>()
//
//        imageId = arrayOf(
//            com.example.diplomatest.R.drawable.img_14,
//            com.example.diplomatest.R.drawable.img_14,
//            com.example.diplomatest.R.drawable.img_14
//        )
//
//        heading = arrayOf(
//            "Test#1",
//            "Test#2",
//            "Test#3"
//        )
//
//        for (i in imageId.indices){
//            val test = Test(imageId[i], heading[i])
//            testArrayList.add(test)
//        }
//    }

    private fun getListeningData(){
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
                    lList.add(newPart)
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
                lAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun getReadingData(){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("ReadingTests").addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    val newPart = dc.document.toObject(ReadingTest::class.java)
                    newPart.id = dc.document.id
                    rList.add(newPart)
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
                rAdapter.notifyDataSetChanged()
            }
        })
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
                    homeList.add(newPart)
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