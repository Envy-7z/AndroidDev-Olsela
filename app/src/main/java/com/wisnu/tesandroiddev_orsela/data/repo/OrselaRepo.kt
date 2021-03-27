package com.wisnu.tesandroiddev_orsela.data.repo

import androidx.lifecycle.LiveData
import com.wisnu.tesandroiddev_orsela.data.db.OrselaDatabase
import com.wisnu.tesandroiddev_orsela.data.db.entity.Orsela

class OrselaRepo(
    private val noteDatabase: OrselaDatabase
) {

    suspend fun insertNote(note: Orsela) = noteDatabase.getOrselaDao().insertNote(note)

    suspend fun updateNote(note: Orsela) = noteDatabase.getOrselaDao().updateNote(note)

    suspend fun deleteNote(note: Orsela) = noteDatabase.getOrselaDao().deleteNote(note)

    suspend fun deleteNoteById(id: Int) = noteDatabase.getOrselaDao().deleteNoteById(id)

    suspend fun clearNote() = noteDatabase.getOrselaDao().clearNote()

    fun getAllNotes(): LiveData<List<Orsela>> = noteDatabase.getOrselaDao().getAllNotes()
    fun getActive(): LiveData<List<Orsela>> = noteDatabase.getOrselaDao().getActive()
    fun getIn(): LiveData<List<Orsela>> = noteDatabase.getOrselaDao().getInActive()


}
