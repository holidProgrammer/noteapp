package com.example.mynoteapp.domain.usecase

import com.example.mynoteapp.domain.model.Note
import com.example.mynoteapp.domain.repository.NoteRepository
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    fun editNote(note: Note) = noteRepository.editNote(note)
}