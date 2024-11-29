package com.example.optivote.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.R
import com.example.optivote.model.SessionDto
import com.example.optivote.model.VoteDto

class RecentSessionAdapter(private val navController: NavController) :
    RecyclerView.Adapter<RecentSessionAdapter.RecentSessionViewHolder>() {

    private var recentSessions: List<SessionDto?>? = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSessionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.votes_history_item, parent, false)
        return RecentSessionViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recentSessions?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecentSessionViewHolder, position: Int) {
        val recentSessions = recentSessions?.get(position)
        if (recentSessions != null) {
            holder.bind(recentSessions)
        }
    }

    fun submitList(newRecentSession: List<SessionDto?>?) {
        recentSessions = newRecentSession
        notifyDataSetChanged()
    }


    inner class RecentSessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sessionTextView: TextView = itemView.findViewById(R.id.textViewSession)
        private val dateTextView: TextView = itemView.findViewById(R.id.textviewDate)
        private val detailsBtn: Button = itemView.findViewById(R.id.detailsBtn)

        @SuppressLint("SetTextI18n")
        fun bind(sessionDto: SessionDto) {
            sessionTextView.text = "الدورة ${sessionDto.number}"
            dateTextView.text = sessionDto.year.toString()
            detailsBtn.text = "تفاصيل"
            detailsBtn.setOnClickListener {
                val sessionId = sessionDto.idSession
                val bundle = Bundle().apply {
                    putSerializable("clickedSession", recentSessions?.get(adapterPosition))
                    putInt("sessionId", sessionId)
                }
                navController.navigate(R.id.historyFragment, bundle)
            }
        }
    }


}