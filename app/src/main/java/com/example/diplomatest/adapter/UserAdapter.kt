package com.example.diplomatest.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomatest.R
import com.example.diplomatest.databinding.ItemListChatBinding
import com.example.diplomatest.model.User
import com.example.diplomatest.ChatActivity

class UserAdapter(var context: Context, var userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(var v: ItemListChatBinding): RecyclerView.ViewHolder(v.root){}

    fun setFilteredList(userlist: ArrayList<User>){
        this.userList = userlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemListChatBinding>(
            inflater, R.layout.item_list_chat, parent,
            false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.v.isUsers =userList[position]

        holder.v.root.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            userList[position].userId

            context.startActivity(intent)
            val context = holder.itemView.context as Activity


            context.overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }



    /*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUserName.text = user.username
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val txtUserName: TextView = view.findViewById(R.id.userName)
        val txtTemp: TextView = view.findViewById(R.id.temp)
    }

 */


}