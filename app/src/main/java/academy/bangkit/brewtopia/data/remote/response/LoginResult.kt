package academy.bangkit.brewtopia.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field: SerializedName("userID")
    val userID: String,

    @field: SerializedName("name")
    val name: String,

    @field: SerializedName("token")
    val token: String
)
