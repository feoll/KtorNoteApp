package com.example.ktornoteapp.data.api

import com.example.ktornoteapp.data.models.Message
import com.example.ktornoteapp.data.models.Note
import com.example.ktornoteapp.data.models.params.LoginParams
import com.example.ktornoteapp.data.models.Token
import com.example.ktornoteapp.data.models.params.CreateNoteParams
import com.example.ktornoteapp.data.models.params.RegistrationParams
import com.example.ktornoteapp.data.models.params.UpdateNoteParams
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

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


    @Headers("Content-Type: application/json")
    @POST("/notes/create")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createNoteParams: CreateNoteParams
    ): Response<Note>

    @Headers("Content-Type: application/json")
    @POST("/notes/update")
    suspend fun update(
        @Header("Authorization") token: String,
        @Body updateNoteParams: UpdateNoteParams
    ): Response<Note>

    @Headers("Content-Type: application/json")
    @DELETE("/notes/delete")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<Message>

    @Headers("Content-Type: application/json")
    @GET("/notes")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): Response<List<Note>>

}