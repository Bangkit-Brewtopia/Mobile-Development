package academy.bangkit.brewtopia.data.remote

import academy.bangkit.brewtopia.data.remote.requestbody.SignInBody
import academy.bangkit.brewtopia.data.remote.requestbody.SignUpBody
import academy.bangkit.brewtopia.data.remote.response.ChatBotResponse
import academy.bangkit.brewtopia.data.remote.response.ScanResponse
import academy.bangkit.brewtopia.data.remote.response.SignInResponse
import academy.bangkit.brewtopia.data.remote.response.SignUpResponse
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

    @POST("/api/user/signup")
    fun signUp(
        @Body signUpBody: SignUpBody
    ): Call<SignUpResponse>

    @POST("/api/user/signin")
    fun signIn(
        @Body signInBody: SignInBody
    ): Call<SignInResponse>
}