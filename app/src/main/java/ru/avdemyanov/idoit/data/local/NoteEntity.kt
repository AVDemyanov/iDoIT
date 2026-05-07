package ru.avdemyanov.idoit.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
)