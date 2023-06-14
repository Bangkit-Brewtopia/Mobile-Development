package academy.bangkit.brewtopia.main.chat

import academy.bangkit.brewtopia.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.overflowarchives.linkpreview.TwitterPreview
import com.overflowarchives.linkpreview.ViewListener

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
    private val messageList: MutableList<Pair<String, String>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val (message, src) = messageList[position]
        when (src) {
            "user" -> holder.bindUserMessage(message)
            "bot" -> holder.bindChatbotMessage(message)
            "link" -> holder.loadUrl(message)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun addMessage(message: String, src: String) {
        messageList.add(Pair(message, src))
        notifyItemInserted(messageList.size - 1)
        logMessageList()
    }

    private fun logMessageList() {
        Log.d("ChatAdapter", "Message List: $messageList")
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userMessageTextView: TextView = itemView.findViewById(R.id.tv_message)
        private val chatbotMessageTextView: TextView = itemView.findViewById(R.id.tv_bot_message)
        private val linkPreview: TwitterPreview = itemView.findViewById(R.id.link_preview)

        fun loadUrl(loadUrl: String) {
            linkPreview.loadUrl(loadUrl, object : ViewListener {
                override fun onFailedToLoad(e: Exception?) {
                    Toast.makeText(itemView.context, "Failed to load: ${e?.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onPreviewSuccess(status: Boolean) {
                    Toast.makeText(itemView.context, "Preview Success", Toast.LENGTH_SHORT).show()
                }
            })
            userMessageTextView.visibility = View.GONE
            chatbotMessageTextView.visibility = View.GONE
        }

        fun bindUserMessage(message: String) {
            userMessageTextView.text = message
            userMessageTextView.visibility = View.VISIBLE
            chatbotMessageTextView.visibility = View.GONE
            linkPreview.visibility = View.GONE
        }

        fun bindChatbotMessage(message: String) {
            chatbotMessageTextView.text = message
            chatbotMessageTextView.visibility = View.VISIBLE
            userMessageTextView.visibility = View.GONE
            linkPreview.visibility = View.GONE
        }
    }
}