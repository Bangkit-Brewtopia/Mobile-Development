package academy.bangkit.brewtopia.data.remote.response

import academy.bangkit.brewtopia.data.remote.response.LoginResult
import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @field: SerializedName("error")
    val error: Boolean,

    @field: SerializedName("message")
    val message: String,

    @field: SerializedName("loginResult")
    val loginResult: LoginResult
)
