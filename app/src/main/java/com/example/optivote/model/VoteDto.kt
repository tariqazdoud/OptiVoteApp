package com.example.optivote.model

import kotlinx.datetime.*

import kotlinx.serialization.Serializable

@Serializable
data class VoteDto(
    val idVote: Long? = null,
    val code: Long? = null,
    val content: String? = null,
    val date: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val totalTime: Long? = null,
    val statut: String? = null,
    val sessionIdFk: Long? = null,
    val title: String? = null
) : java.io.Serializable