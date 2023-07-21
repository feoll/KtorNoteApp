package com.samarth.ktornoteapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ktornoteapp.data.models.LocalNote
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: LocalNote): Long

    @Query("SELECT * FROM LocalNote WHERE locallyDeleted = 0 ORDER BY date DESC")
    fun getAllNotesOrderedByDate(): Flow<List<LocalNote>>

    @Query("DELETE FROM LocalNote WHERE id=:id")
    suspend fun deleteNote(id: Int)

    @Query("UPDATE LocalNote SET locallyDeleted = 1 WHERE id=:id")
    suspend fun deleteNoteLocally(id: Int)

    @Query("SELECT * FROM LocalNote WHERE connected = 0")
    suspend fun getAllConnectedNotes(): List<LocalNote>

    @Query("SELECT * FROM LocalNote WHERE locallyDeleted = 0")
    suspend fun getAllLocallyNotes(): List<LocalNote>
}