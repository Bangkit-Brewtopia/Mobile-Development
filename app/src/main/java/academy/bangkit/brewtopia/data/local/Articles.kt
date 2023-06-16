package academy.bangkit.brewtopia.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Articles(
    val title: String,
    val author: String,
    val love: String,
    val content: String,
    val cover: Int,
    val banner: Int
): Parcelable