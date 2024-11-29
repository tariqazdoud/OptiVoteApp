package com.example.optivote.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.optivote.R
import com.example.optivote.model.VoteRecordDto
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class HistoryDetailsAdapter : RecyclerView.Adapter<HistoryDetailsAdapter.VoteRecordViewHolder>() {
    private var voteRecords: List<VoteRecordDto?>? = emptyList()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VoteRecordViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_details_rv_elemnt, parent, false)
        return VoteRecordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VoteRecordViewHolder, position: Int) {
        val voteRecord = voteRecords?.get(position)
        if (voteRecord != null) {
            holder.bind(voteRecord)
        }
    }

    override fun getItemCount(): Int {
        return voteRecords?.size ?: 0
    }
    fun submitList(newVoteRecords: List<VoteRecordDto?>?){
        voteRecords = newVoteRecords
        notifyDataSetChanged()
    }

    inner class VoteRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val decisionTextView: TextView = itemView.findViewById(R.id.decisionTextView)
        private val shapeableImageView: ShapeableImageView =
            itemView.findViewById(R.id.shapeableImageView)

        fun bind(voteRecord: VoteRecordDto) {
            nameTextView.text = voteRecord.user?.name
            decisionTextView.text = voteRecord.decision
            Picasso.get().load(voteRecord.user?.image?.let { buildImageUrl(it) })
                .into(shapeableImageView)

        }

    }

    private fun buildImageUrl(imageUrl: String): String {
        return imageUrl.replace("http://127.0.0.1", "http://192.168.50.69")
    }

}