package com.wisnu.tesandroiddev_orsela.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.wisnu.tesandroiddev_orsela.data.db.entity.Orsela
import com.wisnu.tesandroiddev_orsela.data.repo.OrselaRepo

class OrselaViewModel(
    private val repository: OrselaRepo
): ViewModel() {

    suspend fun insertNote(note: Orsela) = repository.insertNote(note)

    suspend fun updateNote(note: Orsela) = repository.updateNote(note)

    suspend fun deleteNote(note: Orsela) = repository.deleteNote(note)

    suspend fun deleteNoteById(id: Int) = repository.deleteNoteById(id)

    suspend fun clearNote() = repository.clearNote()

    fun getAllNotes() = repository.getAllNotes()
    fun getActive() = repository.getActive()
    fun getInActive() = repository.getIn()

}