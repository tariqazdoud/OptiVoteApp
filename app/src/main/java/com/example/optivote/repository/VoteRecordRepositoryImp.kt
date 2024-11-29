package com.example.optivote.repository

import com.example.optivote.model.SessionDto
import com.example.optivote.model.VoteDto
import com.example.optivote.model.VoteRecordDto
import com.example.optivote.model.decisionToSend
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VoteRecordRepositoryImp @Inject constructor(private val auth: Auth, private val postgrest: Postgrest) : VoteRecordRepository {
    override suspend fun getVoteByCode(code: Int): VoteDto? {
        return try {
            withContext(Dispatchers.IO) {
                val vote = postgrest.from("vote").select(columns = Columns.list("idVote, content, title")) {
                    filter {
                        eq("code", code)
                    }
                }.decodeSingleOrNull<VoteDto>()
                vote
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getVoteRecords(voteCode:Long): List<VoteRecordDto>? {
        return try {
            withContext(Dispatchers.IO){
                val voteRecords = postgrest.from("decision").select(Columns.raw("idDecision, decision, vote!inner(code),user(id,name,image)")){
                    filter {
                        eq("vote.code",voteCode)
                    }
                }.decodeList<VoteRecordDto>()
                voteRecords
            }
        }catch (e:Exception){
            null
        }
    }




    override suspend fun checkUserDecision(voteCode: Int, userId: Long): List<VoteRecordDto>? {
        return try {
            withContext(Dispatchers.IO){
                val voteRecords = postgrest.from("decision").select(Columns.raw("idDecision, decision, vote!inner(code),user!inner(id)")){
                    filter {
                                eq("user.id",userId)
                                eq("vote.code",voteCode)
                    }
                }
                .decodeList<VoteRecordDto>()
                voteRecords
            }
        }catch (e:Exception){
            null
        }
    }
    override suspend fun getRecentVotes(): List<VoteDto>? {
        return try {
            withContext(Dispatchers.IO){
                val recentVotes = postgrest.from("vote").select(Columns.raw("code, title, date,statut,content")){
                    filter {
                        eq("statut","done")
                    }
                    order("idVote", order = Order.DESCENDING)
                    limit(3)
                }.decodeList<VoteDto>()
                recentVotes
            }
        }catch (e:Exception){
            null
        }
    }
    override suspend fun getAllVotes(): List<VoteDto>? {
        return try {
            withContext(Dispatchers.IO){
                val allVotes = postgrest.from("vote").select(Columns.raw("code, title, date,statut,content")).decodeList<VoteDto>()
                allVotes
            }
        }catch (e:Exception){
            null
        }
    }
    override suspend fun getVotesBySessionId(idSession: Int): List<VoteDto>? {
        return try {
            withContext(Dispatchers.IO) {
               val voteBySession =  postgrest.from("vote").select(Columns.raw("content, date, statut, sessionIdFk, title, code")) {
                    filter {
                        eq("sessionIdFk", idSession)
                    }
                }.decodeList<VoteDto>()
                voteBySession
            }
        } catch (e: Exception) {
            null
        }
    }
    override suspend fun getRecentSessions(): List<SessionDto>? {
        return try {
            withContext(Dispatchers.IO) {
                val recentSessions =
                    postgrest.from("session").select(Columns.raw("idSession, year, number, status")) {
                        filter {
                            eq("status", "done")
                        }
                        order("idSession", order = Order.DESCENDING)
                    }.decodeList<SessionDto>()
                recentSessions
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUpcomingSessions(): List<SessionDto>? {
        return try {
            withContext(Dispatchers.IO) {
                val upcomingSessions = postgrest.from("session").select(Columns.raw("idSession, year, number, status")) {
                    filter {
                        eq("status", "not started")
                    }
                    order("idSession", order = Order.ASCENDING)
                }.decodeList<SessionDto>()
                upcomingSessions
            }
        } catch (e: Exception) {
            null
        }
    }


    override suspend fun submitVote(voteRecord: decisionToSend): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("decision").insert(voteRecord)
                true
            }
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }


    }



