package com.pigeoff.notes.database

import androidx.room.*

@Database(entities = arrayOf(RmNotes::class), version = 1)
abstract class RmDatabase : RoomDatabase() {
    abstract fun notesDAO(): RmNotesDAO
}
