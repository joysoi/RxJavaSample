package com.nikola.rxjavasample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikola.rxjavasample.data.model.UiNotesItem
import com.nikola.rxjavasample.databinding.NoteItemViewBinding

class MainNotesAdapter: ListAdapter<UiNotesItem, MainNotesAdapter.NotesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NoteItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
            val item = getItem(position)
            holder.bindTo(item)
    }

    inner class NotesViewHolder(private val binding: NoteItemViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bindTo(item: UiNotesItem){
            binding.apply {
                textViewTitle.text = item.noteTitle
                textViewBody.text = item.noteBody
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<UiNotesItem>(){
        override fun areItemsTheSame(oldItem: UiNotesItem, newItem: UiNotesItem): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: UiNotesItem, newItem: UiNotesItem): Boolean {
            return oldItem == newItem
        }

    }

}