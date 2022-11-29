package com.example.mynoteapp.data.mapper

import com.example.mynoteapp.data.model.NoteEntity
import com.example.mynoteapp.domain.model.Note

fun Note.toNoteEntity() = NoteEntity(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt
)
fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt
)