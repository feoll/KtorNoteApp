package com.example.ktornoteapp.data.api

import com.example.ktornoteapp.data.models.params.LoginParams
import com.example.ktornoteapp.data.models.Token
import com.example.ktornoteapp.data.models.params.RegistrationParams
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NoteApi {

    @Headers("Content-Type: application/json")
    @POST("/login")
    suspend fun login(
        @Body loginParams: LoginParams
    ): Response<Token>

    @Headers("Content-Type: application/json")
    @POST("/register")
    suspend fun registration(
        @Body registrationParams: RegistrationParams
    ): Response<Token>

}