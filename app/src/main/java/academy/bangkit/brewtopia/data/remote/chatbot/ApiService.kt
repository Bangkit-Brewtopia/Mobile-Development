package academy.bangkit.brewtopia.data.remote.chatbot

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/")
    fun chatbot(
        @Body requestBody: JsonObject
    ): Call<ChatBotResponse>
}