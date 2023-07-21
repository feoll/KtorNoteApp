package com.example.ktornoteapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity
data class LocalNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String,
    var description: String,
    var date: Long = System.currentTimeMillis(),
    var connected: Boolean = false,
    var locallyDeleted: Boolean = false
)
