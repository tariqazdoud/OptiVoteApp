package com.example.optivote.repository


import com.example.optivote.model.SessionDto
import com.example.optivote.model.VoteDto
import com.example.optivote.model.VoteRecordDto
import com.example.optivote.model.decisionToSend

interface VoteRecordRepository {
    suspend fun getVoteByCode(code: Int): VoteDto?
    suspend fun getVoteRecords(voteCode:Long):List<VoteRecordDto>?
    suspend fun submitVote(vote: decisionToSend): Boolean
    suspend fun getAllVotes():List<VoteDto>?
    suspend fun getRecentVotes():List<VoteDto>?
    suspend fun checkUserDecision(voteCode: Int, userId: Long):List<VoteRecordDto>?
    suspend fun getVotesBySessionId(idSession: Int): List<VoteDto>?
    suspend fun getRecentSessions(): List<SessionDto>?
    suspend fun getUpcomingSessions(): List<SessionDto>?

}