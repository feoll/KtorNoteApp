package com.example.ktornoteapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ktornoteapp.data.models.Note
import com.example.ktornoteapp.databinding.ItemNoteBinding

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                binding.cardView.setOnClickListener {
                    onClick?.invoke(note)
                }
                binding.noteText.text = note.title
                binding.noteDescription.text = note.description
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setList(list: List<Note>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val card = differ.currentList[position]
        holder.bind(card)
    }

    override fun getItemCount() = differ.currentList.size

    private var onClick: ((Note) -> Unit)? = null

    fun setOnClickListener(block: (Note) -> Unit) {
        onClick = block
    }
}