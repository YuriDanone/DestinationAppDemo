package com.example.diplomatest.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomatest.R
import com.example.diplomatest.ReadingInfoResultsActivity
import com.example.diplomatest.databinding.ReadingListBinding
import com.example.diplomatest.model.ReadingTest

class ReadingTestResultsAdapter(
    var c: Context, var readinglist:ArrayList<ReadingTest>
): RecyclerView.Adapter<ReadingTestResultsAdapter.TestViewHolder>()
{
    inner class TestViewHolder(var v: ReadingListBinding): RecyclerView.ViewHolder(v.root){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ReadingListBinding>(
            inflater, R.layout.reading_list, parent,
            false)

        return TestViewHolder(v)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.v.isReading =readinglist[position]

        holder.v.root.setOnClickListener{
            val intent = Intent(c, ReadingInfoResultsActivity::class.java)
            readinglist[position].part

            intent.putExtra("id",readinglist.get(position).id)
            intent.putExtra("part",readinglist.get(position).part)
            intent.putExtra("img1",readinglist.get(position).img1)
            intent.putExtra("img2",readinglist.get(position).img2)
            intent.putExtra("img3",readinglist.get(position).img3)
            intent.putExtra("q1", readinglist.get(position).q1)
            intent.putExtra("q2", readinglist.get(position).q2)
            intent.putExtra("q3", readinglist.get(position).q3)
            intent.putExtra("q4", readinglist.get(position).q4)
            intent.putExtra("q5", readinglist.get(position).q5)
            intent.putExtra("q6", readinglist.get(position).q6)
            intent.putExtra("q7", readinglist.get(position).q7)
            intent.putExtra("q8", readinglist.get(position).q8)
            intent.putExtra("q9", readinglist.get(position).q9)
            intent.putExtra("q10", readinglist.get(position).q10)
            intent.putExtra("q11", readinglist.get(position).q11)
            intent.putExtra("q12", readinglist.get(position).q12)
            intent.putExtra("q13", readinglist.get(position).q13)
            intent.putExtra("q14", readinglist.get(position).q14)
            intent.putExtra("q15", readinglist.get(position).q15)
            intent.putExtra("q16", readinglist.get(position).q16)
            intent.putExtra("q17", readinglist.get(position).q17)
            intent.putExtra("q18", readinglist.get(position).q18)
            intent.putExtra("q19", readinglist.get(position).q19)
            intent.putExtra("q20", readinglist.get(position).q20)
            intent.putExtra("q21", readinglist.get(position).q21)
            intent.putExtra("q22", readinglist.get(position).q22)
            intent.putExtra("q23", readinglist.get(position).q23)
            intent.putExtra("q24", readinglist.get(position).q24)
            intent.putExtra("q25", readinglist.get(position).q25)
            intent.putExtra("q26", readinglist.get(position).q26)
            intent.putExtra("q27", readinglist.get(position).q27)
            intent.putExtra("q28", readinglist.get(position).q28)
            intent.putExtra("q29", readinglist.get(position).q29)
            intent.putExtra("q30", readinglist.get(position).q30)
            intent.putExtra("q31", readinglist.get(position).q31)
            intent.putExtra("q32", readinglist.get(position).q32)
            intent.putExtra("q33", readinglist.get(position).q33)
            intent.putExtra("q34", readinglist.get(position).q34)
            intent.putExtra("q35", readinglist.get(position).q35)
            intent.putExtra("q36", readinglist.get(position).q36)
            intent.putExtra("q37", readinglist.get(position).q37)
            intent.putExtra("q38", readinglist.get(position).q38)
            intent.putExtra("q39", readinglist.get(position).q39)
            intent.putExtra("q40", readinglist.get(position).q40)
            intent.putExtra("feedback",readinglist.get(position).feedback)

            c.startActivity(intent)
            val context = holder.itemView.context as Activity


            context.overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)


        }

    }

    override fun getItemCount(): Int {
        return readinglist.size
    }
}