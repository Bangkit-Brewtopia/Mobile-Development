package academy.bangkit.brewtopia.data.remote.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(
    @field: SerializedName("error") val error: Boolean,
    @field: SerializedName("message") val message: String
)