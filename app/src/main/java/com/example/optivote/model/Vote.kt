package com.example.optivote.model

import java.time.LocalTime
import java.util.Date

class Vote (val voteId: Int,
            val joinCode: Int,
            val title: String,
            val content: String,
            val date: Date,
            val startTime: LocalTime,
            val endTime: LocalTime,
            val statue: String,
            val listVoters: List<User>,
            val session: Session)