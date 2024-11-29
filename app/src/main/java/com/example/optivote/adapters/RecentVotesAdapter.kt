package com.example.optivote.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.R
import com.example.optivote.model.VoteDto

class RecentVotesAdapter(private val navController: NavController) :
    RecyclerView.Adapter<RecentVotesAdapter.VoteViewHolder>() {

    private var allVotes: List<VoteDto?>? = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentVotesAdapter.VoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recentvote_recyclerview_item, parent, false
        )
        return VoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecentVotesAdapter.VoteViewHolder, position: Int) {
        val allVotes = allVotes?.get(position)
        if (allVotes != null) {
            holder.bind(allVotes)
        }

    }

    override fun getItemCount(): Int {
        return allVotes?.size ?: 0

    }

    fun submitList(newVoteHistory: List<VoteDto?>?) {
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
            detailsBtn.text = "تفاصيل"
            detailsBtn.setOnClickListener {
                val fragmentId = R.id.historyDetails
                navController.navigate(fragmentId, Bundle().apply {
                    putSerializable("clickedVote", allVotes?.get(adapterPosition))
                })
            }


        }

    }

}