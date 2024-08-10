package com.example.diplomatest.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
//import com.example.testauth.InfoActivity
import com.example.diplomatest.R
import com.example.diplomatest.databinding.ItemListBinding
import com.example.diplomatest.model.DataSource

class HomeAdapter(
    var c: Context, var homelist:ArrayList<DataSource>
):RecyclerView.Adapter<HomeAdapter.HomeViewHolder>()
{
    inner class HomeViewHolder(var v:ItemListBinding): RecyclerView.ViewHolder(v.root){}

    fun setFilteredList(homelist: ArrayList<DataSource>){
        this.homelist = homelist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemListBinding>(
            inflater, R.layout.item_list, parent,
            false)

        return HomeViewHolder(v)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.v.isHomes =homelist[position]

        holder.v.root.setOnClickListener {
            val url = "https://destination.taplink.ws/" // Replace this with your URL
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            c.startActivity(intent)
        }

        /*holder.v.root.setOnClickListener{
            val intent = Intent(c, InfoActivity::class.java)
            homelist[position].name
            intent.putExtra("img",homelist.get(position).img)
            intent.putExtra("name",homelist.get(position).name)
            intent.putExtra("city",homelist.get(position).city)
            intent.putExtra("commis_date",homelist.get(position).commis_date)
            intent.putExtra("description",homelist.get(position).description)
            c.startActivity(intent)
            val context = holder.itemView.context as Activity


            context.overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)
        }*/

    }

    override fun getItemCount(): Int {
        return homelist.size
    }
}