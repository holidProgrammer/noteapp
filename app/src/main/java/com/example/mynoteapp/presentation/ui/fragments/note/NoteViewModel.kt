package com.example.mynoteapp.presentation.ui.fragments.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynoteapp.domain.model.Note
import com.example.mynoteapp.domain.usecase.DeleteNoteUseCase
import com.example.mynoteapp.domain.usecase.EditNoteUseCase
import com.example.mynoteapp.domain.usecase.GeAllNotesUseCase
import com.example.mynoteapp.presentation.core.Resource
import com.example.mynoteapp.presentation.core.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotesUseCase: GeAllNotesUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _deleteNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val deleteNoteState = _deleteNoteState.asStateFlow()

    private val _editNoteState = MutableStateFlow<UIState<Note>>(UIState.Empty())
    val editNoteState = _editNoteState.asStateFlow()

    private val _getAllNotesState = MutableStateFlow<UIState<List<Note>>>(UIState.Empty())
    val getAllNotesState = _getAllNotesState.asStateFlow()


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase.deleteNote(note).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _deleteNoteState.value = UIState.Loading()
                    }
                    is Resource.Error -> {
                        _deleteNoteState.value = UIState.Error(resource.message!!)
                    }
                    is Resource.Success -> {
                        if (resource.data != null)
                            _deleteNoteState.value = UIState.Success(resource.data)
                    }
                }
            }
        }
    }

    fun editNote(note: Note) {
        viewModelScope.launch {
            editNoteUseCase.editNote(note).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _editNoteState.value = UIState.Loading()
                    }
                    is Resource.Error -> {
                        _editNoteState.value = UIState.Error(resource.message!!)
                    }
                    is Resource.Success -> {
                        if (resource.data != null)
                            _editNoteState.value = UIState.Success(resource.data)
                    }
                }
            }
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.getAllNotes().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _getAllNotesState.value = UIState.Loading()
                    }
                    is Resource.Error -> {
                        _getAllNotesState.value = UIState.Error(resource.message!!)
                    }
                    is Resource.Success -> {
                        if (resource.data != null)
                            _getAllNotesState.value = UIState.Success(resource.data)
                    }
                }
            }
        }
    }
}