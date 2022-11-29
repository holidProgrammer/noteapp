package com.example.mynoteapp.presentation.ui.fragments.note.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynoteapp.databinding.ItemNoteBinding
import com.example.mynoteapp.domain.model.Note

class NoteAdapter(
    private val onItemClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ItemNoteViewHolder>() {

    private var list = listOf<Note>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Note>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemNoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(note: Note) = with(binding) {
            txtTitle.text = note.title
            txtDescription.text = note.description
            itemView.setOnLongClickListener {
                onItemClick.invoke(note)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNoteViewHolder {
        return ItemNoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemNoteViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}