package com.wisnu.tesandroiddev_orsela.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wisnu.tesandroiddev_orsela.data.db.entity.Orsela

@Dao
interface OrselaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //if some data is same/conflict, it'll be replace with new data.
    suspend fun insertNote(note: Orsela)

    @Update
    suspend fun updateNote(note: Orsela)

    @Delete
    suspend fun deleteNote(note: Orsela)

    @Query("SELECT * FROM orsela_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Orsela>>

    //genap
    @Query("SELECT * FROM orsela_table WHERE id %2 = 0")
    fun getInActive(): LiveData<List<Orsela>>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines


    //ganjil
    @Query("SELECT * FROM orsela_table WHERE id %2 <> 0")
    fun getActive(): LiveData<List<Orsela>>


    @Query("DELETE FROM orsela_table")
    suspend fun clearNote()

    @Query("DELETE FROM orsela_table WHERE id = :id") //you can use this too, for delete note by id.
    suspend fun deleteNoteById(id: Int)
}