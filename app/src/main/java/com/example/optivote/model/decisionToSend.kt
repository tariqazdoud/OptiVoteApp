package com.example.optivote.model

import kotlinx.serialization.Serializable

@Serializable
data class decisionToSend(
    val decision: String,
    val voteIdFk: Long,
    val userIdFk: Long
)