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
import com.example.optivote.databinding.UpcomingSessionItemBinding
import com.example.optivote.model.SessionDto
import com.example.optivote.model.VoteDto
import kotlinx.datetime.toJavaLocalDate

class UpcomingSessionAdapter(private val navController: NavController) :
    RecyclerView.Adapter<UpcomingSessionAdapter.UpcomingSessionViewHolder>() {

    private var upcomingSessions: List<SessionDto?>? = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingSessionAdapter.UpcomingSessionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.upcoming_session_item, parent, false)
        return UpcomingSessionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UpcomingSessionAdapter.UpcomingSessionViewHolder, position: Int) {
        val upcomingSessions = upcomingSessions?.get(position)
        if (upcomingSessions != null) {
            holder.bind(upcomingSessions)
        }

    }

    override fun getItemCount(): Int {
        return upcomingSessions?.size ?: 0

    }

    fun submitList(newUpcomingSession: List<SessionDto?>?) {
        upcomingSessions = newUpcomingSession
        notifyDataSetChanged()
    }

    inner class UpcomingSessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sessionTextView: TextView = itemView.findViewById(R.id.textViewSession)
        private val dateTextView: TextView = itemView.findViewById(R.id.textviewDate)
        private val detailsBtn: Button = itemView.findViewById(R.id.detailsBtn)


        fun bind(sessionDto: SessionDto) {
            sessionTextView.text = "الدورة ${sessionDto.number}"
            dateTextView.text = sessionDto.year.toString()
            detailsBtn.text = "تفاصيل"
            detailsBtn.setOnClickListener {
                val sessionId = sessionDto.idSession
                val bundle = Bundle().apply {
                    putSerializable("clickedSession", upcomingSessions?.get(adapterPosition))
                    putInt("sessionId", sessionId)
                }
                navController.navigate(R.id.historyFragment, bundle)
            }

        }

    }
}
