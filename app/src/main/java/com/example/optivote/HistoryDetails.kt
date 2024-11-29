package com.example.optivote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.ViewModel.SignInViewModel
import com.example.optivote.ViewModel.VoteRecordViewModel
import com.example.optivote.adapters.HistoryDetailsAdapter
import com.example.optivote.databinding.FragmentHistoryDetailsBinding
import com.example.optivote.model.UserInInfo
import com.example.optivote.model.VoteDto
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryDetails : Fragment() {
    private var _binding: FragmentHistoryDetailsBinding? = null
    private val signInViewModel: SignInViewModel by viewModels()
    private val voteRecordViewModel: VoteRecordViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        BottomSheetBehavior.from(binding.sheets).apply {
            peekHeight = 50
            this.state = STATE_COLLAPSED
        }
        binding.sheets.elevation = resources.getDimension(R.dimen.bottom_sheet_elevation)

        val voteByCode = arguments?.getSerializable("clickedVote") as VoteDto
        Log.d("idVote","$voteByCode")

            binding.voteTitle.text = voteByCode.title
            binding.voteContent.text = voteByCode.content

        voteRecordViewModel.agreedCountLiveData.observe(viewLifecycleOwner) { count ->
            Log.d("agreed", "$count")
            binding.agreeProgressBar.progress = count
            binding.AgreeCountTv.text = "$count P"
        }
        voteRecordViewModel.disagreedCountLiveData.observe(viewLifecycleOwner) { count ->
            Log.d("disagreed", "$count")
            binding.DisagreeProgressBar.progress = count
            binding.DisagreeCountTv.text = "$count P"
        }
        voteRecordViewModel.neutralCountLiveData.observe(viewLifecycleOwner) { count ->
            Log.d("neutral", "$count")
            binding.NeutralProgressBar.progress = count
            binding.NeutralCountTv.text = "$count P"
        }


        voteRecordViewModel.totalCountLiveData.observe(viewLifecycleOwner) { count ->
            binding.agreeProgressBar.max = count
            binding.DisagreeProgressBar.max = count
            binding.NeutralProgressBar.max = count
        }


        val recyclerView : RecyclerView = binding.OthersRV
        val adapter : HistoryDetailsAdapter = HistoryDetailsAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        voteRecordViewModel.voteRecordsLiveDate.observe(viewLifecycleOwner){
            voteRecords->
            val filteredList = voteRecords?.filter { it?.user?.id != UserInInfo.id }
            voteRecords?.filter { it?.user?.id == UserInInfo.id }?.
            map {
                if (it != null) {
                    binding.currentUserName.visibility = View.VISIBLE
                    binding.currentUserName.text = it.user?.name ?: ""
                    binding.currentUserDecision.visibility = View.VISIBLE
                    binding.currentUserDecision.text = it.decision
                    binding.currentUserImg.visibility = View.VISIBLE
                    Picasso.get().load(it.user?.image?.let { UserInInfo.buildImageUrl(it) }).
                    into(binding.currentUserImg)
                }

            }

            adapter.submitList(filteredList)
        }
        voteByCode.code?.let { voteRecordViewModel.getVotRecords(it) }






        return view


    }
    /*fun getVoteRecords(code : Int) {
        val agreedUsers = mutableListOf<VoteRecordDto>()
        val disagreedUsers = mutableListOf<VoteRecordDto>()
        val neutralUsers = mutableListOf<VoteRecordDto>()

        voteRecordViewModel.voteRecordsLiveDate.observe(viewLifecycleOwner){
                voteData->
            if (voteData != null) {
                for (data in voteData){
                    if (data != null) {
                        if (data.decision == "ok"){
                            agreedUsers.add(data)
                        }else if (data.decision == "no"){
                            disagreedUsers.add(data)
                        }else{
                            neutralUsers.add(data)
                        }

                    }
                }

            }
            Log.d("agreed","$agreedUsers")
            Log.d("disagreed","$disagreedUsers")
            Log.d("neutral","$neutralUsers")
        }
        voteRecordViewModel.getVotRecords(code)
    }*/


}