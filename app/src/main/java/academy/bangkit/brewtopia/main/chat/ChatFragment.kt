package academy.bangkit.brewtopia.main.chat

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.data.remote.ApiService
import academy.bangkit.brewtopia.data.remote.config.ApiConfigChat
import academy.bangkit.brewtopia.data.remote.response.ChatBotResponse
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatFragment : Fragment() {
    private lateinit var apiService: ApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = ApiConfigChat.getApiService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        recyclerView = view.findViewById(R.id.rv_messages)
        val sendButton: ImageView = view.findViewById(R.id.btn_send)
        val messageInput: EditText = view.findViewById(R.id.et_message)

        val layoutManager = LinearLayoutManager(context)
        chatAdapter = ChatAdapter()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = chatAdapter

//        processChatBotResponse("Hai, Selamat Datang di ChatBot Brewtopia!")
//        processChatBotResponse("Gunakan Bahasa Indonesia dan Kamu dapat bertanya apa saja tentang Kopi disini :D")

        processChatBotResponse("Hi, Welcome to Brewtopia ChatBot!")
        processChatBotResponse("Please use English and feel free to ask anything about coffee here :D")

        sendButton.setOnClickListener {
            val userInput = messageInput.text.toString().trim()
            if (userInput.isNotEmpty()) {
                chatAdapter.addMessage(userInput,"user")
                sendChatMessage(userInput)
                messageInput.text.clear()
            }
        }

        return view
    }
    private fun sendChatMessage(message: String) {
        val apiService = ApiConfigChat.getApiService()

        val requestBody = JsonObject()
        requestBody.addProperty("input", message)

        val call = apiService.chatbot(requestBody)
        call.enqueue(object : Callback<ChatBotResponse> {
            override fun onResponse(
                call: Call<ChatBotResponse>,
                response: Response<ChatBotResponse>
            ) {
                if (response.isSuccessful) {
                    val chatBotResponse = response.body()
                    val msg: String = chatBotResponse?.message ?: ""
                    val data: List<String> = chatBotResponse?.data ?: emptyList()

                    // Process the chat-bot response
                    if (msg == "success") {
                        if (data.size == 1) {
                            processChatBotResponse(data[0])
                        } else {
                            var firstMessage = true
                            for (i in data) {
                                if (firstMessage) {
                                    processChatBotResponse(i)
                                    firstMessage = false
                                } else {
                                    val pattern = "\\((.*?)\\)"
                                    val regex = Regex(pattern)
                                    val matchResult = regex.find(i)
                                    val link = matchResult?.groupValues?.get(1) ?: ""
                                    processChatBotResponseLink(link)
                                }
                            }
                        }
                    }
                } else {
                    // Handle API call error
                    val errorMessage = "API call failed with code: ${response.code()}"
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ChatBotResponse>, t: Throwable) {
                // Handle API call failure
                val errorMessage = "API call failed: ${t.message}"
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun processChatBotResponse(message: String) {
        chatAdapter.addMessage(message, "bot")
        recyclerView.scrollToPosition(chatAdapter.itemCount.minus(1))
    }

    private fun processChatBotResponseLink(message: String) {
        chatAdapter.addMessage(message, "link")
        recyclerView.scrollToPosition(chatAdapter.itemCount.minus(1))
    }
}