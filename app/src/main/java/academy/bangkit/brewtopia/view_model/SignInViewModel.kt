package academy.bangkit.brewtopia.view_model

import academy.bangkit.brewtopia.data.remote.config.ApiConfigUser
import academy.bangkit.brewtopia.data.remote.requestbody.SignInBody
import academy.bangkit.brewtopia.data.remote.response.SignInResponse
import academy.bangkit.brewtopia.preferences.UserPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun signIn(email: String, pass: String) {
        _isLoading.value = true
        ApiConfigUser
            .getApiService()
            .signIn(SignInBody(email, pass))
            .enqueue(object : Callback<SignInResponse> {
                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                            viewModelScope.launch {
                                userPreferences.saveToken(responseBody.loginResult.token)
                                userPreferences.saveName(responseBody.loginResult.name)
                                _isSuccess.value = true
                            }
                        }
                    } else {
                        _isSuccess.value = false
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                    _isSuccess.value = false
                    _isLoading.value = false
                }

            })
    }
}