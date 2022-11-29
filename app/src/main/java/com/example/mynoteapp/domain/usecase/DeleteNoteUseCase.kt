package com.example.mynoteapp.domain.usecase

import com.example.mynoteapp.domain.model.Note
import com.example.mynoteapp.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    fun deleteNote(note: Note) = noteRepository.deleteNote(note)
}