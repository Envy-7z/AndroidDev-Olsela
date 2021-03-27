package com.wisnu.tesandroiddev_orsela.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wisnu.tesandroiddev_orsela.R
import com.wisnu.tesandroiddev_orsela.data.db.entity.Orsela
import kotlinx.android.synthetic.main.item_note.view.*



class MainAdapter(val context : Context, private val listener: (Orsela) -> Unit) : ListAdapter<Orsela, MainAdapter.NoteViewHolder>(
    DiffUtilNote()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        if (position % 2 == 0){
            holder.cardViewItem.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
            holder.text.text = "Online Booking Enabled"
            holder.active.text = ""
        } else {
            holder.cardViewItem.setBackgroundColor(ContextCompat.getColor(context,R.color.grey))
            holder.text.text = ""
        }

        holder.bindItem(item, listener)
    }

    fun getNoteAt(position: Int): Orsela {
        return getItem(position)
    }

    class NoteViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val cardViewItem = view.cardView
        val text = view.textDua
        val active = view.tvActive
        fun bindItem(note: Orsela, listener: (Orsela) -> Unit) {
            view.apply {
                tvNama.text = note.name

            }
            view.setOnClickListener {
                listener(note)
            }
        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<Orsela>() {
        override fun areItemsTheSame(oldItem: Orsela, newItem: Orsela): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Orsela, newItem: Orsela): Boolean {
            return newItem == oldItem
        }
    }
}