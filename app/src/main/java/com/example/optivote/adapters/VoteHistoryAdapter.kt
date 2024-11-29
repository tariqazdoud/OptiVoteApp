package com.example.optivote.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.R
import com.example.optivote.model.VoteDto
import com.example.optivote.model.VoteRecordDto
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class VoteHistoryAdapter(private val navController: NavController) : RecyclerView.Adapter<VoteHistoryAdapter.VoteViewHolder>() {
    private var allVotes: List<VoteDto?>? = emptyList()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.votes_history_recyclerview_item, parent, false)
        return VoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VoteViewHolder, position: Int) {
        val allVote = allVotes?.get(position)
        if (allVote != null) {
            holder.bind(allVote)
        }
    }

    override fun getItemCount(): Int {
        return allVotes?.size ?: 0
    }
    fun submitList(newVoteHistory: List<VoteDto?>?){
        allVotes = newVoteHistory
        notifyDataSetChanged()
    }

    inner class VoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTxtView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val dateTextView: TextView = itemView.findViewById(R.id.textviewDate)
        private val detailsBtn: Button = itemView.findViewById(R.id.detailsBtn)



        fun bind(voteDto: VoteDto) {
                titleTxtView.text = voteDto.title
                dateTextView.text = voteDto.date.toString()

                if(voteDto.statut == "done"){
                    detailsBtn.text = "تفاصيل"
                    detailsBtn.setOnClickListener {
                        val fragmentId = R.id.historyDetails
                        navController.navigate(fragmentId, Bundle().apply {
                            putSerializable("clickedVote", allVotes?.get(adapterPosition))
                        })
                }
                }else{
                    detailsBtn.text = "جاري"
                }

        }

    }}

