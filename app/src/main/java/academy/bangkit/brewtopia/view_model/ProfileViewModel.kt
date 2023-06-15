package academy.bangkit.brewtopia.view_model

import academy.bangkit.brewtopia.preferences.UserPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.runBlocking

class ProfileViewModel (private val userPreferences: UserPreferences) : ViewModel(){
    fun getName(): LiveData<String> {
        return userPreferences.getName().asLiveData()
    }

    fun clearSession() {
        runBlocking {
            userPreferences.clearSession()
        }
    }
}