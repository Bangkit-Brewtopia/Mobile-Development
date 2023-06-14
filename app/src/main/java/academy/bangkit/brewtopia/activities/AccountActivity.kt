package academy.bangkit.brewtopia.activities

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.databinding.ActivityAccountBinding
import academy.bangkit.brewtopia.main.MainActivity
import academy.bangkit.brewtopia.preferences.UserPreferences
import academy.bangkit.brewtopia.view_model.AccountViewModel
import academy.bangkit.brewtopia.view_model.SignInViewModel
import academy.bangkit.brewtopia.view_model.SignUpViewModel
import academy.bangkit.brewtopia.view_model.ViewModelFactory
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var signInViewModel: SignInViewModel
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        userPreferences = UserPreferences.getInstance(dataStore)
        accountViewModel =
            ViewModelProvider(this, ViewModelFactory(userPreferences))[AccountViewModel::class.java]
        binding = ActivityAccountBinding.inflate(layoutInflater)
        accountViewModel.getToken().observe(this) {
            if (it.isNotEmpty()) {
                moveActivity(MainActivity::class.java)
            } else {
                setContentView(binding.root)
                binding.apply {
                    btnMainSignin.setOnClickListener {
                        dialogSignIn()
                    }
                    btnMainSignup.setOnClickListener {
                        dialogSignUp()
                    }
                }
            }
        }

    }

    private fun dialogSignIn() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.signin_bottom)
        val btnSignIn = dialog.findViewById<Button>(R.id.btn_signin)

        userPreferences = UserPreferences.getInstance(dataStore)
        signInViewModel =
            ViewModelProvider(this, ViewModelFactory(userPreferences))[SignInViewModel::class.java]

        fun showLoadingSignIn(isLoading: Boolean) {
            dialog.findViewById<ProgressBar>(R.id.pb_signin)?.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        btnSignIn?.setOnClickListener {
            val email = dialog.findViewById<EditText>(R.id.et_email_signin)?.text.toString()
            val pass = dialog.findViewById<EditText>(R.id.et_signin_password)?.text.toString()
            signInViewModel.signIn(email, pass)
        }

        signInViewModel.isSuccess.observe(this) {
            if (it) {
                moveActivity(MainActivity::class.java)
                finish()
                Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show()
            }
        }

        signInViewModel.isLoading.observe(this) {
            showLoadingSignIn(it)
        }

        dialog.show()
    }

    @SuppressLint("CutPasteId")
    private fun dialogSignUp() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.signup_bottom)
        val btnSignUp = dialog.findViewById<Button>(R.id.btn_signup)
        signUpViewModel = SignUpViewModel(application)

        fun showLoadingSignUp(isLoading: Boolean) {
            dialog.findViewById<ProgressBar>(R.id.pb_signup)?.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        btnSignUp?.setOnClickListener {
            val name = dialog.findViewById<EditText>(R.id.et_name_signup)?.text.toString()
            val email = dialog.findViewById<EditText>(R.id.et_email_signup)?.text.toString()
            val pass = dialog.findViewById<EditText>(R.id.et_password_signup)?.text.toString()
            when {
                name == "" -> {
                    dialog.findViewById<EditText>(R.id.et_name_signup)?.error =
                        getString(R.string.empty_name)
                }

                email == "" -> {
                    dialog.findViewById<EditText>(R.id.et_email_signup)?.error =
                        getString(R.string.empty_email)
                }

                pass == "" -> {
                    dialog.findViewById<EditText>(R.id.et_password_signup)?.error =
                        getString(R.string.empty_pass)
                }

                else -> {
                    signUpViewModel.signUp(name, email, pass, this)
                }
            }
        }

        signUpViewModel.isFailed.observe(this) {
            showSignIn(it)
        }

        signUpViewModel.isLoading.observe(this) {
            showLoadingSignUp(it)
        }

        signUpViewModel.signUpMsg.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }

    private fun moveActivity(to: Class<*>) {
        val intent = Intent(this, to)
        this.startActivity(intent)
    }

    private fun showSignIn(isFailed: Boolean) {
        if (!isFailed) {
            dialogSignIn()
        }
    }
}