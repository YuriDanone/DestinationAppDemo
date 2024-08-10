package com.example.diplomatest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomatest.HomeFragment
import com.example.diplomatest.R
import com.example.diplomatest.TestActivity
import com.example.diplomatest.model.Test
import com.google.android.material.imageview.ShapeableImageView

class TestAdapter(var c: Context, private val testList: ArrayList<Test>) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.TestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_home,
            parent, false)
        return TestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TestAdapter.TestViewHolder, position: Int) {
        val currentItem = testList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading
        holder.itemView.setOnClickListener {
            val intent = Intent(c, TestActivity::class.java)
            c.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    class TestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleImage : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
    }
}