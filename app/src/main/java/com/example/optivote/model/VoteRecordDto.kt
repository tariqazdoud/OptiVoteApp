package com.example.optivote.model

import kotlinx.serialization.Serializable

@Serializable
data class VoteRecordDto (val idDecision: Long? = null,
                          val decision: String? = null,
                          val user: UserDto? = null,
                          val vote: VoteDto? = null):java.io.Serializable