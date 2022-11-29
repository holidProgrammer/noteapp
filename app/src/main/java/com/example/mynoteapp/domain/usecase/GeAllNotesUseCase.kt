package com.example.mynoteapp.domain.usecase

import com.example.mynoteapp.domain.repository.NoteRepository
import javax.inject.Inject

class GeAllNotesUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    fun getAllNotes() = noteRepository.getAllNotes()
}