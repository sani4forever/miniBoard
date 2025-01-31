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
    private val clickListener: (Long) -> Unit,
    private val longClickListener: (Long) -> Unit
) : RecyclerView.Adapter<TextItemAdapter.ViewHolder>() {

    private val messages: MutableList<TextItem> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.username)
        private val createdAtTextView: TextView = itemView.findViewById(R.id.created_at)
        private val messageTextView: TextView = itemView.findViewById(R.id.message_text)

        fun bind(
            message: TextItem,
            clickListener: (Long) -> Unit,
            longClickListener: (Long) -> Unit
        ) {
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

            itemView.setOnLongClickListener {
                longClickListener.invoke(message.id.toLong())
                true

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
        val message = messages[position]
        viewHolder.bind(message, clickListener, longClickListener)

        val marginStart = message.commentDepth * 20
        val params = viewHolder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        params.marginStart = marginStart
        viewHolder.itemView.layoutParams = params


    }

    fun submitList(newMessages: List<TextItem>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }
}

