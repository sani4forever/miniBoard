package com.example.miniboard.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniboard.R
import com.example.miniboard.retrofit.TextItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TextItemAdapter(
    private val context: Context,
    private val clickListener: (Long) -> Unit
) : RecyclerView.Adapter<TextItemAdapter.ViewHolder>() {

    private val messages: MutableList<TextItem> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.username)
        private val createdAtTextView: TextView = itemView.findViewById(R.id.created_at)
        private val messageTextView: TextView = itemView.findViewById(R.id.message_text)

        fun bind(message: TextItem, clickListener: (Long) -> Unit) {
            usernameTextView.text = message.username
            createdAtTextView.text = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                .format(Date(message.createdAt.toLong() * 1000))
            messageTextView.text = message.text

            if (message.comments.isNotEmpty()) {
                itemView.setOnClickListener {
                    clickListener.invoke(message.id.toLong())
                }
            } else {
                itemView.setOnClickListener(null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(messages[position], clickListener)
    }

    fun submitList(newMessages: List<TextItem>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }
}

