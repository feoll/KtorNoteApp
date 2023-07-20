package com.example.ktornoteapp.data.repositories

import com.example.ktornoteapp.data.models.Token
import com.example.ktornoteapp.data.models.params.LoginParams
import com.example.ktornoteapp.data.models.params.RegistrationParams
import com.example.ktornoteapp.utils.Resource

interface UserRepository {
    suspend fun login(param: LoginParams): Resource<Token>
    suspend fun registration(param: RegistrationParams): Resource<Token>
    suspend fun getToken(): Resource<Token>
    suspend fun logout()
}