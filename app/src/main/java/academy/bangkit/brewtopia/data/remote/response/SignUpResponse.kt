package academy.bangkit.brewtopia.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @field: SerializedName("message")
    val message: String,

    @field: SerializedName("error")
    val error: Boolean
)
