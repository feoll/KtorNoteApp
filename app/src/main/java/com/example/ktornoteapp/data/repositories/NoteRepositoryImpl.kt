package com.example.ktornoteapp.data.repositories

import android.content.Context
import com.example.ktornoteapp.data.api.NoteApi
import com.example.ktornoteapp.data.manager.SessionManager
import com.example.ktornoteapp.data.models.LocalNote
import com.example.ktornoteapp.data.models.Message
import com.example.ktornoteapp.data.models.Note
import com.example.ktornoteapp.data.models.params.CreateNoteParams
import com.example.ktornoteapp.data.models.params.UpdateNoteParams
import com.example.ktornoteapp.utils.Resource
import com.example.ktornoteapp.utils.isNetworkConnected
import com.google.gson.Gson
import com.samarth.ktornoteapp.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class NoteRepositoryImpl(
    private val context: Context,
    private val api: NoteApi,
    private val sessionManager: SessionManager,
    private val gson: Gson,
    private val dao: NoteDao
) : NoteRepository {
    override suspend fun create(param: CreateNoteParams): Resource<Note> {
        val id = dao.insertNote(LocalNote(
            title = param.title,
            description = param.description
        ))
        try {
            if (!isNetworkConnected(context)) {
                return Resource.Error("No Internet connection")
            }

            val token = sessionManager.getToken() ?: return Resource.Error("Not authorized")
            val createNoteResponse = api.create(
                token = "Bearer $token",
                createNoteParams = param
            )

            if (createNoteResponse.isSuccessful) {
                val body = createNoteResponse.body()
                body?.let { note ->
                    dao.insertNote(LocalNote(
                        id = id.toInt(),
                        title = param.title,
                        description = param.description,
                        connected = true
                    ))
                    return Resource.Success(note)
                }
            }

            val errorBody = createNoteResponse.errorBody()
            if (errorBody != null) {
                val message = gson.fromJson(errorBody.string(), Message::class.java)
                if (message != null) return Resource.Error(message.message)
            }

            return Resource.Error("Something went wrong")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Some Problem")
        }
    }

    override suspend fun update(param: UpdateNoteParams): Resource<Note> {
        dao.insertNote(LocalNote(
            id = param.id,
            title = param.title,
            description = param.description
        ))
        try {
            if (!isNetworkConnected(context)) {
                return Resource.Error("No Internet connection")
            }

            val token = sessionManager.getToken() ?: return Resource.Error("Not authorized")
            val updateNoteResponse = api.update(
                token = "Bearer $token",
                updateNoteParams = param
            )

            if (updateNoteResponse.isSuccessful) {
                val body = updateNoteResponse.body()
                body?.let { note ->
                    dao.insertNote(LocalNote(
                        id = param.id,
                        title = param.title,
                        description = param.description,
                        connected = true
                    ))
                    return Resource.Success(note)
                }
            }

            val errorBody = updateNoteResponse.errorBody()
            if (errorBody != null) {
                val message = gson.fromJson(errorBody.string(), Message::class.java)
                if (message != null) return Resource.Error(message.message)
            }

            return Resource.Error("Something went wrong")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Some Problem")
        }
    }

    override suspend fun delete(id: Int): Resource<Message> {
        dao.deleteNoteLocally(id)
        try {
            if (!isNetworkConnected(context)) {
                return Resource.Error("No Internet connection")
            }

            val token = sessionManager.getToken() ?: return Resource.Error("Not authorized")
            val notesResponse = api.delete(token = "Bearer $token", id)

            if (notesResponse.isSuccessful) {
                val body = notesResponse.body()
                body?.let { message ->
                    return Resource.Success(message)
                }
            }

            val errorBody = notesResponse.errorBody()
            if (errorBody != null) {
                val message = gson.fromJson(errorBody.string(), Message::class.java)
                if (message != null) return Resource.Error(message.message)
            }

            return Resource.Error("Something went wrong")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Some Problem")
        }
    }

    override fun getAll(): Flow<List<LocalNote>> {
        return dao.getAllNotesOrderedByDate()
    }

    override suspend fun getAllRemote(): Resource<List<Note>> {
        try {
            if (!isNetworkConnected(context)) {
                return Resource.Error("No Internet connection")
            }

            val token = sessionManager.getToken() ?: return Resource.Error("Not authorized")
            val notesResponse = api.getAll(token = "Bearer $token")

            if (notesResponse.isSuccessful) {
                val body = notesResponse.body()
                body?.let { note ->
                    return Resource.Success(note)
                }
            }

            val errorBody = notesResponse.errorBody()
            if (errorBody != null) {
                val message = gson.fromJson(errorBody.string(), Message::class.java)
                if (message != null) return Resource.Error(message.message)
            }

            return Resource.Error("Something went wrong")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Some Problem")
        }
    }
}