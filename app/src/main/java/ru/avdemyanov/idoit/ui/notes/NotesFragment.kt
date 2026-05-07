package ru.avdemyanov.idoit.ui.notes

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import ru.avdemyanov.idoit.R
import ru.avdemyanov.idoit.databinding.FragmentNotesBinding


class NotesFragment : Fragment(R.layout.fragment_notes) {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotesBinding.bind(view)

        val adapter = NoteAdapter { viewModel.deleteNote(it) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val note = adapter.currentList[position]
                viewModel.deleteNote(note)
                Snackbar.make(binding.root, R.string.note_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.dialog_cancel) { viewModel.addNote(note.title, note.description) }
                    .show()
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerView)

        binding.fab.setOnClickListener { showAddNoteDialog() }
    }

    private fun showAddNoteDialog() {
        val context = requireContext()
        val padding = (20 * resources.displayMetrics.density).toInt()

        val editTitle = EditText(context).apply { hint = "Название"; setSingleLine() }
        val editDesc = EditText(context).apply { hint = "Описание" }

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(padding, padding / 2, padding, padding / 2)
            addView(editTitle)
            addView(editDesc)
        }

        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.dialog_new_note)
            .setView(layout)
            .setPositiveButton(R.string.dialog_add) { _, _ ->
                val title = editTitle.text.toString()
                val desc = editDesc.text.toString()
                if (title.isNotBlank()) viewModel.addNote(title, desc)
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
