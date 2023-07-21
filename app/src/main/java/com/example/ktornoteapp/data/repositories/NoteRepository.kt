package com.example.ktornoteapp.data.repositories

import com.example.ktornoteapp.data.models.LocalNote
import com.example.ktornoteapp.data.models.Message
import com.example.ktornoteapp.data.models.Note
import com.example.ktornoteapp.data.models.params.CreateNoteParams
import com.example.ktornoteapp.data.models.params.UpdateNoteParams
import com.example.ktornoteapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun create(param: CreateNoteParams): Resource<Note>
    suspend fun update(param: UpdateNoteParams): Resource<Note>
    suspend fun delete(id: Int): Resource<Message>
    fun getAll(): Flow<List<LocalNote>>
    suspend fun getAllRemote(): Resource<List<Note>>
}