package com.example.diplomatest.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomatest.R
import com.example.diplomatest.WritingInfoActivity
import com.example.diplomatest.databinding.WritingListBinding
import com.example.diplomatest.model.WritingTest

class WritingTestAdapter(
    var c: Context, var writinglist:ArrayList<WritingTest>
): RecyclerView.Adapter<WritingTestAdapter.TestViewHolder>()
{
    inner class TestViewHolder(var v: WritingListBinding): RecyclerView.ViewHolder(v.root){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<WritingListBinding>(
            inflater, R.layout.writing_list, parent,
            false)

        return TestViewHolder(v)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.v.isWriting =writinglist[position]

        holder.v.root.setOnClickListener{
            val intent = Intent(c, WritingInfoActivity::class.java)
            writinglist[position].part

            intent.putExtra("id",writinglist.get(position).id)
            intent.putExtra("part",writinglist.get(position).part)
            intent.putExtra("img1",writinglist.get(position).img1)
            intent.putExtra("img2",writinglist.get(position).img2)
            intent.putExtra("q1", writinglist.get(position).q1)
            intent.putExtra("q2", writinglist.get(position).q2)
            intent.putExtra("wordCount1", writinglist.get(position).wordCount1)
            intent.putExtra("wordCount2", writinglist.get(position).wordCount2)
            intent.putExtra("feedback1", writinglist.get(position).feedback1)
            intent.putExtra("feedback2", writinglist.get(position).feedback2)

            c.startActivity(intent)
            val context = holder.itemView.context as Activity


            context.overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)


        }

    }

    override fun getItemCount(): Int {
        return writinglist.size
    }
}