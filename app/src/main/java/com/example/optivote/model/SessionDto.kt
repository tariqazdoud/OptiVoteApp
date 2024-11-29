package com.example.optivote.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(val idSession: Int,
                      val year: LocalDate,
                      val number: Int,
                      val status: String):java.io.Serializable