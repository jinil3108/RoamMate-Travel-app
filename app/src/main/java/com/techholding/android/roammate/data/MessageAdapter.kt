package com.techholding.android.roammate.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techholding.android.roammate.databinding.ItemMessageBinding
import com.techholding.android.roammate.model.Message
import com.techholding.android.roammate.utils.RECEIVE_ID
import com.techholding.android.roammate.utils.SEND_ID

class MessageAdapter:RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private var messageList = mutableListOf<Message>()
    inner class MessageViewHolder(private val binding: ItemMessageBinding): RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener{
                messageList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
        fun bind(message: Message) {
            when (message.id) {
                SEND_ID -> {
                    binding.tvmessage.apply {
                        text = message.message
                        visibility = View.VISIBLE
                    }
                    binding.tvbotmessage.visibility = View.GONE
                }
                RECEIVE_ID -> {
                    binding.tvbotmessage.apply {
                        text = message.message
                        visibility = View.VISIBLE
                    }
                    binding.tvmessage.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding =
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messageList[position])
    }
    override fun getItemCount(): Int {
        return try {
            messageList.size
        } catch (ex: Exception) {
            0
        }
    }
    fun insertMessage(message: Message){
        messageList.add(message)
        notifyItemInserted(messageList.size - 1)
    }
}