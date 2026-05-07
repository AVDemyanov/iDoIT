package ru.avdemyanov.idoit.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.avdemyanov.idoit.data.local.NoteEntity
import ru.avdemyanov.idoit.databinding.ItemNoteBinding


class NoteAdapter(private val onDelete: (NoteEntity) -> Unit) :
    ListAdapter<NoteEntity, NoteAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteEntity) {
            binding.textTitle.text = note.title
            binding.textDescription.text = note.description
            binding.root.setOnLongClickListener {
                onDelete(note)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(old: NoteEntity, new: NoteEntity) = old.id == new.id
        override fun areContentsTheSame(old: NoteEntity, new: NoteEntity) = old == new
    }
}