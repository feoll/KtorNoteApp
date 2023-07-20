package com.example.ktornoteapp.data.manager


interface SessionManager {
    suspend fun updateSession(token: String)
    suspend fun getToken(): String?
    suspend fun logout()
}