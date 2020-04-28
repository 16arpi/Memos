package com.pigeoff.notes.database

import androidx.room.*

@Dao
interface RmNotesDAO {
    @Query("SELECT * FROM RmNotes ORDER BY id DESC")
    fun getAllNotes(): List<RmNotes>

    @Query("SELECT * FROM RmNotes WHERE id LIKE :id")
    fun getNote(id: Int) : RmNotes

    @Update
    fun updateNote(note: RmNotes?)

    @Insert
    fun newNote(note: RmNotes?)

    @Delete
    fun deleteNote(note: RmNotes?)
}
