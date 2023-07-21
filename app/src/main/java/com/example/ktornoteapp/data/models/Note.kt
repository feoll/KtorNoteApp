package com.example.ktornoteapp.data.models

data class Note(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val createdAt: Long,
)