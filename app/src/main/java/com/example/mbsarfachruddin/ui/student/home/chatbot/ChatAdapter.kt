package com.example.mbsarfachruddin.ui.student.home.chatbot

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemChatBinding

class ChatAdapter(private val list: MutableList<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChatViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvMessage.text = item.message

        if (item.isUser) {
            holder.binding.bubbleContainer.setBackgroundResource(R.drawable.bubble_sent)

            (holder.binding.bubbleContainer.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.END
            }

        } else {
            holder.binding.bubbleContainer.setBackgroundResource(R.drawable.bubble_received)

            (holder.binding.bubbleContainer.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.START
            }
        }
    }

    fun addMessage(chat: ChatMessage) {
        list.add(chat)
        notifyItemInserted(list.size - 1)
    }
}