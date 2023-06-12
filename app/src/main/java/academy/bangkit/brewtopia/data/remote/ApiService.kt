package academy.bangkit.brewtopia.data.remote

import academy.bangkit.brewtopia.data.remote.response.ChatBotResponse
import academy.bangkit.brewtopia.data.remote.response.ScanResponse
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/")
    fun chatbot(
        @Body requestBody: JsonObject
    ): Call<ChatBotResponse>

    @POST("predict")
    fun uploadImg(
        @Body body: RequestBody
    ): Call<ScanResponse>
}