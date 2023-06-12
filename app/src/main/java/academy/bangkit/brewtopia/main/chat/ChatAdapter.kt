package academy.bangkit.brewtopia.main.chat

import academy.bangkit.brewtopia.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
    private val messageList: MutableList<Pair<String, Boolean>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val (message, isUserMessage) = messageList[position]
        if (isUserMessage) {
            holder.bindUserMessage(message)
        } else {
            holder.bindChatbotMessage(message)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun addMessage(message: String, isUserMessage: Boolean) {
        messageList.add(Pair(message, isUserMessage))
        notifyItemInserted(messageList.size - 1)
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userMessageTextView: TextView = itemView.findViewById(R.id.tv_message)
        private val chatbotMessageTextView: TextView =
            itemView.findViewById(R.id.tv_bot_message)

        fun bindUserMessage(message: String) {
            userMessageTextView.text = message
            userMessageTextView.visibility = View.VISIBLE
            chatbotMessageTextView.visibility = View.GONE
        }

        fun bindChatbotMessage(message: String) {
            chatbotMessageTextView.text = message
            chatbotMessageTextView.visibility = View.VISIBLE
            userMessageTextView.visibility = View.GONE
        }
    }
}