package com.example.miniboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class TextItemAdapter() : RecyclerView.Adapter<TextItemAdapter.ViewHolder>() {
    private val messages: MutableList<TextItem> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.username)
        private val createdAtTextView: TextView = itemView.findViewById(R.id.created_at)
        private val messageTextView: TextView = itemView.findViewById(R.id.message_text)

        // Привязка данных к элементу разметки
        fun bind(message: TextItem) {
            usernameTextView.text = message.username
            createdAtTextView.text = message.createdAt
            messageTextView.text = message.text
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(messages[position])
    }

    fun submitList(newMessages: List<TextItem>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }
}
