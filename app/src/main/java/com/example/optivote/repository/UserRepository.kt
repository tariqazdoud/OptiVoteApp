package com.example.optivote.repository

import com.example.optivote.model.UserDto

interface UserRepository {
    suspend fun getUserInfo(userEmail:String):UserDto
    suspend fun updateAlreadySignedInState(id:Long,email:String):Boolean
}