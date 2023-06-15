package academy.bangkit.brewtopia.data.remote.config

import academy.bangkit.brewtopia.BuildConfig
import academy.bangkit.brewtopia.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfigChat {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor: HttpLoggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val retrofit = Retrofit.Builder().baseUrl("https://chatbot-en-api-zwqpt72g4a-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(ApiService::class.java)
        }
    }
}