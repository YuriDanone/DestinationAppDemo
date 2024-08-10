package com.example.diplomatest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.diplomatest.ListeningActivity
import com.example.diplomatest.ReadingActivity
import com.example.diplomatest.WritingActivity
import com.example.diplomatest.R
import com.example.diplomatest.databinding.FragmentHomeBinding
import com.example.diplomatest.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    lateinit var binding: FragmentResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.listeningBtn.setOnClickListener {
            val intent = Intent(requireContext(), ListeningResultsActivity::class.java)
            startActivity(intent)
        }

        binding.readingBtn.setOnClickListener {
            val intent = Intent(requireContext(), ReadingResultsActivity::class.java)
            startActivity(intent)
        }

        binding.writingBtn.setOnClickListener {
            val intent = Intent(requireContext(), WritingResultsActivity::class.java)
            startActivity(intent)
        }
    }
}
