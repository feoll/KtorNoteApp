package com.samarth.ktornoteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ktornoteapp.data.models.LocalNote

@Database(entities = [LocalNote::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}