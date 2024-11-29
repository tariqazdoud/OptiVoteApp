package com.example.optivote.repository



interface AuthenticationRepository {

    suspend fun signIn(email:String, password:String):Boolean
    suspend fun getCurrentUserEmail():String
    //logout function
    suspend fun logout(): Boolean
    suspend fun updatePassword(password: String): Boolean
}