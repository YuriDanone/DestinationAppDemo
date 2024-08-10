package com.example.diplomatest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomatest.adapter.UserAdapter
import com.example.diplomatest.databinding.FragmentChatBinding
import com.example.diplomatest.model.User
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class ChatFragment : Fragment() {

    lateinit var mDataBase: FirebaseFirestore
    private lateinit var userList: ArrayList<User>

    private lateinit var adapter : UserAdapter
    private lateinit var recyclerView: RecyclerView

//    lateinit var imageId: Array<Int>
//    lateinit var heading: Array<String>
//    lateinit var test: ArrayList<String>

    lateinit var binding: FragmentChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        userList = ArrayList()


        adapter = UserAdapter(requireContext(),userList)
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(requireContext())

        //binding.recyclerViewChat.setHasFixedSize(true)
        binding.recyclerViewChat.adapter = adapter

        getUsersData()
    }

    private fun getUsersData(){
        mDataBase = FirebaseFirestore.getInstance()
        mDataBase.collection("Users").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    val newUser = dc.document.toObject(User::class.java)
                    newUser.userId = dc.document.id
                    userList.add(newUser)
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
                adapter.notifyDataSetChanged()
            }
        })
    }

    /*
    private fun dataInitialize(){
        chatArrayList = arrayListOf<Chat>()

        imageId = arrayOf(
            com.example.diplomatest.R.drawable.img_12,
            com.example.diplomatest.R.drawable.img_12,
            com.example.diplomatest.R.drawable.img_12
        )

        heading = arrayOf(
            "Teacher",
            "Croup",
            "User1"
        )

        for (i in imageId.indices){
            val chat = Chat(imageId[i], heading[i])
            chatArrayList.add(chat)
        }
    }

     */

}