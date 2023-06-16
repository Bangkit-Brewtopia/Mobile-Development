package academy.bangkit.brewtopia.viewmodel

import academy.bangkit.brewtopia.preferences.UserPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class AccountViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    fun getToken(): LiveData<String> {
        return userPreferences.getToken().asLiveData()
    }
}