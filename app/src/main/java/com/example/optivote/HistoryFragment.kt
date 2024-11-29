package com.example.optivote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.ViewModel.VoteRecordViewModel
import com.example.optivote.adapters.HistoryDetailsAdapter
import com.example.optivote.adapters.VoteHistoryAdapter
import com.example.optivote.databinding.FragmentHistoryBinding
import com.example.optivote.databinding.FragmentHistoryDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val voteRecordViewModel: VoteRecordViewModel by viewModels()
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        var recyclerView : RecyclerView = binding.recycleView
        var adapter = VoteHistoryAdapter(findNavController())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        val sessionId = arguments?.getInt("sessionId")

        voteRecordViewModel.votesBySessionIdLiveData.observe(viewLifecycleOwner){

                allVote -> adapter.submitList(allVote)
            Log.d("all vote","$allVote")
        }
        voteRecordViewModel.getVotesBySessionId(sessionId)

        return view


    }


}