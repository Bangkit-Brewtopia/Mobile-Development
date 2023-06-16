package academy.bangkit.brewtopia.about

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Developer(
    val name: String,
    val profileDev: Int,
    val linkedin: String
) : Parcelable
