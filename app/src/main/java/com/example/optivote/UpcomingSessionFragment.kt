package com.example.optivote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.ViewModel.VoteRecordViewModel
import com.example.optivote.adapters.UpcomingSessionAdapter
import com.example.optivote.databinding.FragmentUpcomingSessionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingSessionFragment : Fragment() {

    private var _binding: FragmentUpcomingSessionBinding? = null
    private val binding get() = _binding!!
    private val voteRecordViewModel : VoteRecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingSessionBinding.inflate(inflater, container, false)
        val view = binding.root

        var recyclerView : RecyclerView = binding.recycleView
        var adapter = UpcomingSessionAdapter(findNavController())
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        voteRecordViewModel.upcomingSessionLiveData.observe(viewLifecycleOwner){
                upcomingSessions -> adapter.submitList(upcomingSessions)
            Log.d("Upcoming Sessions","$upcomingSessions")
        }
        voteRecordViewModel.getUpcomingSessions()

        return view
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}