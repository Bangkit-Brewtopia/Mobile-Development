package academy.bangkit.brewtopia.view_model

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.data.remote.config.ApiConfigUser
import academy.bangkit.brewtopia.data.remote.requestbody.SignUpBody
import academy.bangkit.brewtopia.data.remote.response.SignUpResponse
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val _isFailed = MutableLiveData<Boolean>()
    val isFailed: LiveData<Boolean> = _isFailed

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _signUpMsg = MutableLiveData<String>()
    val signUpMsg: LiveData<String> = _signUpMsg


    fun signUp(name: String, email: String, pass: String, context: Context) {
        _isLoading.value = true
        val service = ApiConfigUser.getApiService().signUp(SignUpBody(name, email, pass))
        service.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isFailed.value = responseBody.error
                        _signUpMsg.value = responseBody.message
                    }
                } else {
                    _isFailed.value = true
                    val gson = JSONObject(response.errorBody()?.string().toString())
                    val msg = gson.getString("message")
                    _signUpMsg.value = msg
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                _isFailed.value = true
                _isLoading.value = false
                _signUpMsg.value = noInternet()
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun noInternet(): String {
        return getApplication<Application>().resources.getString(R.string.failed_connection)
    }
}