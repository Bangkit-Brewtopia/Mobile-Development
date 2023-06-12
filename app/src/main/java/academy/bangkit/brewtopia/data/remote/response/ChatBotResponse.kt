package academy.bangkit.brewtopia.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class ChatBotResponse(
    @field:SerializedName("message") val message: String,
    @field:SerializedName("data") val data: List<String>
) : Parcelable