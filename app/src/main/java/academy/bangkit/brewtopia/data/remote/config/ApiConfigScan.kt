package academy.bangkit.brewtopia.data.remote.config

import academy.bangkit.brewtopia.data.remote.ApiService
import com.google.gson.GsonBuilder
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfigScan {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder().baseUrl("https://predict-zwqpt72g4a-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client).build()
            return retrofit.create(ApiService::class.java)
        }
    }
}