package com.example.miniboard.recyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.miniboard.R
import com.example.miniboard.retrofit.TextItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TextItemAdapter(private val context: Context) : RecyclerView.Adapter<TextItemAdapter.ViewHolder>() {

    private val messages: MutableList<TextItem> = mutableListOf()

    class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.username)
        private val createdAtTextView: TextView = itemView.findViewById(R.id.created_at)
        private val messageTextView: TextView = itemView.findViewById(R.id.message_text)
        private val commentsLayout: LinearLayout = itemView.findViewById(R.id.comments_layout)

        fun bind(message: TextItem) {
            usernameTextView.text = message.username
            createdAtTextView.text = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(
                Date(message.createdAt.toLong() * 1000)
            )
            messageTextView.text = message.text
            commentsLayout.removeAllViews()

            if (message.comments.isNotEmpty()) {
                for (comment in message.comments) {
                    addCommentView(comment)
                }
            }
        }

        private fun addCommentView(comment: TextItem) {
            val commentView = TextView(context).apply {
                text = comment.text
                setPadding(16, 4, 0, 4)
                setTextColor(Color.BLACK)
                textSize = 14f
            }
            commentsLayout.addView(commentView)

            // Добавляем вложенные комментарии рекурсивно
            if (comment.comments.isNotEmpty()) {
                for (nestedComment in comment.comments) {
                    val nestedCommentView = TextView(context).apply {
                        text = nestedComment.text
                        setPadding(32, 4, 0, 4)
                        setTextColor(Color.GRAY)
                        textSize = 12f
                    }
                    commentsLayout.addView(nestedCommentView)
                    addCommentView(nestedComment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item, parent, false)
        return ViewHolder(view, context)
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
