package com.example.diplomatest.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomatest.AdminInfoActivity
//import com.example.diplomatest.InfoActivity
import com.example.diplomatest.R
import com.example.diplomatest.databinding.ItemListBinding
import com.example.diplomatest.model.DataSource

class AdminHomeAdapter(
    var c: Context, var homelist:ArrayList<DataSource>
): RecyclerView.Adapter<AdminHomeAdapter.HomeViewHolder>()
{
    inner class HomeViewHolder(var v: ItemListBinding): RecyclerView.ViewHolder(v.root){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemListBinding>(
            inflater, R.layout.item_list, parent,
            false)

        return HomeViewHolder(v)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.v.isHomes =homelist[position]

        holder.v.root.setOnClickListener{
            val intent = Intent(c, AdminInfoActivity::class.java)
            homelist[position].name

            intent.putExtra("id",homelist.get(position).id)

            intent.putExtra("img",homelist.get(position).img)
            intent.putExtra("name",homelist.get(position).name)
            intent.putExtra("city",homelist.get(position).city)
            intent.putExtra("commis_date",homelist.get(position).commis_date)
            intent.putExtra("description",homelist.get(position).description)
            c.startActivity(intent)
            val context = holder.itemView.context as Activity


            context.overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)


        }

    }

    override fun getItemCount(): Int {
        return homelist.size
    }
}