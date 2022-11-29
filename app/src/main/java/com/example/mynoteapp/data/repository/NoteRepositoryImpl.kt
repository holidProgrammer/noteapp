package com.example.mynoteapp.data.repository

import com.example.mynoteapp.presentation.core.BaseRepository
import com.example.mynoteapp.presentation.core.Resource
import com.example.mynoteapp.data.local.NoteDao
import com.example.mynoteapp.data.mapper.toNote
import com.example.mynoteapp.data.mapper.toNoteEntity
import com.example.mynoteapp.domain.model.Note
import com.example.mynoteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository, BaseRepository() {

    override fun addNote(note: Note): Flow<Resource<Unit>> = doRequest {
        noteDao.addNote(note.toNoteEntity())
    }

    override fun deleteNote(note: Note): Flow<Resource<Unit>> = doRequest {
        noteDao.deleteNote(note.toNoteEntity())
    }

    override fun editNote(note: Note): Flow<Resource<Note>> = flow {
        Resource.Loading(null)
        try {
            val data = noteDao.editNote(note.toNoteEntity())
            Resource.Success(data)
        } catch (ioException: IOException) {
            Resource.Error(ioException.localizedMessage ?: "Unknown error")
        }
    }

    override fun getAllNotes(): Flow<Resource<List<Note>>> = flow {
        Resource.Loading(data = null)
        try {
            val data = noteDao.gAllNotes().map { it.toNote() }
            Resource.Success(data)
        } catch (ioException: IOException) {
            Resource.Error(ioException.localizedMessage ?: "Unknown error")
        }
    }
}