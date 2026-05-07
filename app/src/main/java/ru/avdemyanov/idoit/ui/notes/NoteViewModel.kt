package ru.avdemyanov.idoit.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.avdemyanov.idoit.data.local.AppDatabase
import ru.avdemyanov.idoit.data.local.NoteEntity

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).noteDao()
    val allNotes: LiveData<List<NoteEntity>> = dao.getAllNotes()

    fun addNote(title: String, description: String) = viewModelScope.launch {
        dao.insert(NoteEntity(title = title, description = description))
    }

    fun deleteNote(note: NoteEntity) = viewModelScope.launch {
        dao.delete(note)
    }
}