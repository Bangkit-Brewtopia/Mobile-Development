package academy.bangkit.brewtopia.account

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.databinding.ActivityProfileBinding
import academy.bangkit.brewtopia.main.MainActivity
import academy.bangkit.brewtopia.preferences.UserPreferences
import academy.bangkit.brewtopia.viewmodel.ProfileViewModel
import academy.bangkit.brewtopia.viewmodel.ViewModelFactory
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var profileViewModel: ProfileViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userPreferences = UserPreferences.getInstance(datastore)
        profileViewModel =
            ViewModelProvider(this, ViewModelFactory(userPreferences))[ProfileViewModel::class.java]
        profileViewModel.getName().observe(this) {
            if (it.isNotEmpty()) {
                binding.tvNameProfile.text = "Hi, $it"
            }
        }
        binding.btnLogout.setOnClickListener{
            MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setMessage(R.string.logout_confirm)
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(R.string.confirm) { _, _ ->
                    profileViewModel.clearSession()
                    startActivity(Intent(this@ProfileActivity, AccountActivity::class.java))
                    finish()
                    Toast.makeText(this, R.string.logged_out, Toast.LENGTH_SHORT).show()
                }
                .show()
        }
        binding.ivLogoutIcon.setOnClickListener{

            MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setMessage(R.string.logout_confirm)
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(R.string.confirm) { _, _ ->
                    profileViewModel.clearSession()
                    startActivity(Intent(this@ProfileActivity, AccountActivity::class.java))
                    finish()
                    Toast.makeText(this, R.string.logged_out, Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}