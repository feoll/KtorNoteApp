package com.example.ktornoteapp.data.models.params

data class UpdateNoteParams(
    val id: Int,
    val title: String,
    val description: String
)