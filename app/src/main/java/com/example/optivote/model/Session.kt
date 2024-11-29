package com.example.optivote.model

import kotlinx.datetime.*

class Session (val sessionId: Int,
               val sessionNumber: Int,
               val year: LocalDate,
               val startDate: LocalDate,
               val endDate: LocalDate)