package com.example.diplomatest.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomatest.ListeningInfoResultsActivity
import com.example.diplomatest.R
import com.example.diplomatest.databinding.ListeningListBinding
import com.example.diplomatest.model.ListeningTest

class ListeningTestResultsAdapter(
    var c: Context, var listeninglist:ArrayList<ListeningTest>
): RecyclerView.Adapter<ListeningTestResultsAdapter.TestViewHolder>()
{
    inner class TestViewHolder(var v: ListeningListBinding): RecyclerView.ViewHolder(v.root){}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ListeningListBinding>(
            inflater, R.layout.listening_list, parent,
            false)

        return TestViewHolder(v)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.v.isListening =listeninglist[position]

        holder.v.root.setOnClickListener{
            val intent = Intent(c, ListeningInfoResultsActivity::class.java)
            listeninglist[position].part

            intent.putExtra("id",listeninglist.get(position).id)
            intent.putExtra("part",listeninglist.get(position).part)
            intent.putExtra("audio1",listeninglist.get(position).audio1)
            intent.putExtra("audio2",listeninglist.get(position).audio2)
            intent.putExtra("audio3",listeninglist.get(position).audio3)
            intent.putExtra("audio4",listeninglist.get(position).audio4)
            intent.putExtra("img1",listeninglist.get(position).img1)
            intent.putExtra("img2",listeninglist.get(position).img2)
            intent.putExtra("img3",listeninglist.get(position).img3)
            intent.putExtra("img4",listeninglist.get(position).img4)
            intent.putExtra("q1", listeninglist.get(position).q1)
            intent.putExtra("q2", listeninglist.get(position).q2)
            intent.putExtra("q3", listeninglist.get(position).q3)
            intent.putExtra("q4", listeninglist.get(position).q4)
            intent.putExtra("q5", listeninglist.get(position).q5)
            intent.putExtra("q6", listeninglist.get(position).q6)
            intent.putExtra("q7", listeninglist.get(position).q7)
            intent.putExtra("q8", listeninglist.get(position).q8)
            intent.putExtra("q9", listeninglist.get(position).q9)
            intent.putExtra("q10", listeninglist.get(position).q10)
            intent.putExtra("q11", listeninglist.get(position).q11)
            intent.putExtra("q12", listeninglist.get(position).q12)
            intent.putExtra("q13", listeninglist.get(position).q13)
            intent.putExtra("q14", listeninglist.get(position).q14)
            intent.putExtra("q15", listeninglist.get(position).q15)
            intent.putExtra("q16", listeninglist.get(position).q16)
            intent.putExtra("q17", listeninglist.get(position).q17)
            intent.putExtra("q18", listeninglist.get(position).q18)
            intent.putExtra("q19", listeninglist.get(position).q19)
            intent.putExtra("q20", listeninglist.get(position).q20)
            intent.putExtra("q21", listeninglist.get(position).q21)
            intent.putExtra("q22", listeninglist.get(position).q22)
            intent.putExtra("q23", listeninglist.get(position).q23)
            intent.putExtra("q24", listeninglist.get(position).q24)
            intent.putExtra("q25", listeninglist.get(position).q25)
            intent.putExtra("q26", listeninglist.get(position).q26)
            intent.putExtra("q27", listeninglist.get(position).q27)
            intent.putExtra("q28", listeninglist.get(position).q28)
            intent.putExtra("q29", listeninglist.get(position).q29)
            intent.putExtra("q30", listeninglist.get(position).q30)
            intent.putExtra("q31", listeninglist.get(position).q31)
            intent.putExtra("q32", listeninglist.get(position).q32)
            intent.putExtra("q33", listeninglist.get(position).q33)
            intent.putExtra("q34", listeninglist.get(position).q34)
            intent.putExtra("q35", listeninglist.get(position).q35)
            intent.putExtra("q36", listeninglist.get(position).q36)
            intent.putExtra("q37", listeninglist.get(position).q37)
            intent.putExtra("q38", listeninglist.get(position).q38)
            intent.putExtra("q39", listeninglist.get(position).q39)
            intent.putExtra("q40", listeninglist.get(position).q40)
            intent.putExtra("feedback", listeninglist.get(position).feedback)

            c.startActivity(intent)
            val context = holder.itemView.context as Activity


            context.overridePendingTransition(R.anim.slide_enter_right, R.anim.slide_exit_left)


        }

    }

    override fun getItemCount(): Int {
        return listeninglist.size
    }
}