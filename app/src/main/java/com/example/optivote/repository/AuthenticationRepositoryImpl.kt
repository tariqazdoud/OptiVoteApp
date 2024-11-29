package com.example.optivote.repository

import android.util.Log
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.Phone
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val auth:Auth) : AuthenticationRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWith(Email){
                this.email = email
                this.password = password
            }
            true
        }catch (e:Exception){
            false
        }
    }

    override suspend fun getCurrentUserEmail(): String {
        return auth.retrieveUserForCurrentSession(updateSession = true).email.toString()
    }
    //implementation of logout function
    override suspend fun logout(): Boolean {
        return try {
            auth.signOut()
            true
        } catch (e: Exception) {
            Log.e("LogoutError", "Failed to log out", e)
            false
        }
    }
    override suspend fun updatePassword(password: String): Boolean {
        return try {
            auth.updateUser {
                this.password = password
            }
            Log.d("updated", "updated password successfully")
            true
        } catch (e: Exception) {
            Log.d("updatePasswordError", "Update password failed", e)
            false
        }
    }



}