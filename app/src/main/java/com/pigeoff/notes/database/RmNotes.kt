package com.pigeoff.notes.database

import androidx.room.*

@Entity
data class RmNotes (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var titre: String? = null,
    var content: String? = null
)