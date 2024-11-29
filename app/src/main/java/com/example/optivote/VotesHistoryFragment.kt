package com.example.optivote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.ViewModel.VoteRecordViewModel
import com.example.optivote.adapters.RecentSessionAdapter
import com.example.optivote.databinding.FragmentVotesHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VotesHistoryFragment : Fragment() {

    private var _binding: FragmentVotesHistoryBinding? = null
    private val binding get() = _binding!!
    private val voteRecordViewModel : VoteRecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVotesHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        var recyclerView : RecyclerView = binding.recycleView
        var adapter = RecentSessionAdapter(findNavController())
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        voteRecordViewModel.recentSessionLiveData.observe(viewLifecycleOwner){
                recentSessions -> adapter.submitList(recentSessions)
            Log.d("Recent Sessions","$recentSessions")
        }
        voteRecordViewModel.getRecentSessions()

        return view
    }
}