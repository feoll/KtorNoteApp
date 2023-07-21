package com.example.ktornoteapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktornoteapp.data.models.Note
import com.example.ktornoteapp.data.models.params.CreateNoteParams
import com.example.ktornoteapp.data.repositories.NoteRepository
import com.example.ktornoteapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {

    private val _noteState = MutableSharedFlow<Resource<Note>>()
    val noteState = _noteState.asSharedFlow()

    private val _allNoteState = MutableSharedFlow<Resource<List<Note>>>()
    val allNoteState = _allNoteState.asSharedFlow()

    fun createNote(param: CreateNoteParams) = viewModelScope.launch {
        _noteState.emit(noteRepository.create(param = param))
    }

    fun getAllNotes() = viewModelScope.launch {
        _allNoteState.emit(noteRepository.getAllRemote())
    }

}