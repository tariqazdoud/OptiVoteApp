package com.example.optivote.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.optivote.model.SessionDto
import com.example.optivote.model.VoteDto
import com.example.optivote.model.VoteRecordDto
import com.example.optivote.model.decisionToSend
import com.example.optivote.repository.VoteRecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoteRecordViewModel @Inject constructor(private val voteRecordRepository: VoteRecordRepository) :
    ViewModel() {
    /*private val _voteLiveDate = MutableLiveData<VoteDto?>()
    val voteLiveDate: MutableLiveData<VoteDto?> = _voteLiveDate*/

    private val _voteRecordsLiveDate = MutableLiveData<List<VoteRecordDto?>?>()
    val voteRecordsLiveDate: MutableLiveData<List<VoteRecordDto?>?> = _voteRecordsLiveDate

    private val _agreedCountLiveData = MutableLiveData<Int>()
    private val _disagreedCountLiveData = MutableLiveData<Int>()
    private val _neutralCountLiveData = MutableLiveData<Int>()

    val agreedCountLiveData: LiveData<Int> = _agreedCountLiveData
    val disagreedCountLiveData: LiveData<Int> = _disagreedCountLiveData
    val neutralCountLiveData: LiveData<Int> = _neutralCountLiveData


    private val _totalCountLiveData = MutableLiveData<Int>()
    val totalCountLiveData: LiveData<Int> = _totalCountLiveData

    private val _allVotesLiveData = MutableLiveData<List<VoteDto?>?>()
    val allVotesLiveData: MutableLiveData<List<VoteDto?>?> = _allVotesLiveData


    private val _recentVotesLiveData = MutableLiveData<List<VoteDto?>?>()
    val recentVotesLiveData: MutableLiveData<List<VoteDto?>?> = _recentVotesLiveData

    private val _votesBySessionIdLiveData = MutableLiveData<List<VoteDto?>?>()
    val votesBySessionIdLiveData:  MutableLiveData<List<VoteDto?>?> = _votesBySessionIdLiveData

    private val _recentSessionLiveData = MutableLiveData<List<SessionDto>?>()
    val recentSessionLiveData: LiveData<List<SessionDto>?> = _recentSessionLiveData

    private val _upcomingSessionLiveData = MutableLiveData<List<SessionDto>?>()
    val upcomingSessionLiveData: LiveData<List<SessionDto>?> = _upcomingSessionLiveData

    /*private val _checkUserDecisionLiveDate = MutableLiveData<List<VoteRecordDto?>?>()
    val checkUserDecisionLiveDate: MutableLiveData<List<VoteRecordDto?>?> = _checkUserDecisionLiveDate*/

    fun submitVote(decision: String, user: Long, vote: Long) {
        viewModelScope.launch {
            val voteRecord = decisionToSend(
                decision = decision,
                voteIdFk = vote,
                userIdFk = user
            )
            voteRecordRepository.submitVote(voteRecord)
        }
    }


    /*private val _currentUserDecisionLiveData = MutableLiveData<VoteRecordDto?>()
    val currentUserDecisionLiveData: MutableLiveData<VoteRecordDto?> = _currentUserDecisionLiveData*/


    /*fun getVoteByCode(code: Int) {
        viewModelScope.launch {
            val result = voteRecordRepository.getVoteByCode(code)
            _voteLiveDate.postValue(result)
        }
    }*/

    fun getVotRecords(code: Long) {
        viewModelScope.launch {
            val result = voteRecordRepository.getVoteRecords(code)
            _voteRecordsLiveDate.postValue(result)
            updateCounts(result)
        }

    }

    private fun updateCounts(voteData: List<VoteRecordDto>?) {
        var agreed = 0
        var disagreed = 0
        var neutral = 0
        var total = 0
        voteData?.forEach { data ->
            when (data.decision) {
                "موافق" -> agreed++
                "رافض" -> disagreed++
                else -> neutral++
            }
        }
        total = agreed + disagreed + neutral
        _agreedCountLiveData.postValue(agreed)
        _disagreedCountLiveData.postValue(disagreed)
        _neutralCountLiveData.postValue(neutral)
        _totalCountLiveData.postValue(total)
    }

    fun getAllVotes() {
        viewModelScope.launch {
            val results = voteRecordRepository.getAllVotes()
            _allVotesLiveData.postValue(results)
        }
    }

    fun getRecentVote() {
        viewModelScope.launch {
            val results = voteRecordRepository.getRecentVotes()
            _recentVotesLiveData.postValue(results)
        }
    }
   /* fun checkUserDecision(voteCode: Int, userId: Long){
        viewModelScope.launch {
            val result = voteRecordRepository.checkUserDecision(voteCode,userId)
            _checkUserDecisionLiveDate.postValue(result)
        }
    }*/
   private val _voteResultLiveData = MutableLiveData<VoteResult?>()
    val voteResultLiveData: LiveData<VoteResult?> = _voteResultLiveData

    fun clearVoteResult() {
        _voteResultLiveData.value = null
    }
    fun proceedToVote(codeEnteredByUser: Int, userId: Long) {
        viewModelScope.launch {
            val voteDto = voteRecordRepository.getVoteByCode(codeEnteredByUser)
            if (voteDto != null) {
                val voteCheck = voteRecordRepository.checkUserDecision(codeEnteredByUser, userId)
                if (voteCheck.isNullOrEmpty()) {
                    _voteResultLiveData.postValue(VoteResult.Success(voteDto))
                } else {
                    _voteResultLiveData.postValue(VoteResult.AlreadyVoted)
                }
            } else {
                _voteResultLiveData.postValue(VoteResult.CodeMismatch)
            }
        }
    }

    sealed class VoteResult {
        data class Success(val voteDto: VoteDto) : VoteResult()
        object AlreadyVoted : VoteResult()
        object CodeMismatch : VoteResult()
    }

    fun getVotesBySessionId(sessionId: Int?) {
        viewModelScope.launch {
            val results = sessionId?.let { voteRecordRepository.getVotesBySessionId(it) }
            _votesBySessionIdLiveData.postValue(results)
        }
    }

    fun getRecentSessions() {
        viewModelScope.launch {
            val results = voteRecordRepository.getRecentSessions()
            _recentSessionLiveData.postValue(results)
        }
    }

    fun getUpcomingSessions() {
        viewModelScope.launch {
            val results = voteRecordRepository.getUpcomingSessions()
            _upcomingSessionLiveData.postValue(results)
        }
    }

    /*fun currentUserDecision(voteData: List<VoteRecordDto?>?, currentUserId: Int){
        voteData?.forEach { data->
            if (data != null) {
                if(data.user?.id==currentUserId){
                    _currentUserDecisionLiveData.postValue(data)
                }
            }
        }
    }*/


}