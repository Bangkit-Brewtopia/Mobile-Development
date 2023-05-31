package academy.bangkit.brewtopia.splash

import academy.bangkit.brewtopia.MainActivity
import academy.bangkit.brewtopia.databinding.ActivitySplashLastBinding
import academy.bangkit.brewtopia.databinding.ActivitySplashMiddleBinding
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class LastSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashLastBinding
    private val delayTime: Long = 50
    private var progressHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashLastBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        showProgress()
    }

    private fun showProgress() {
        Handler(Looper.getMainLooper()).postDelayed({
            val progress = binding.loadingSplashLast.progress + 1
            binding.loadingSplashLast.progress = progress
            val options = ActivityOptions.makeCustomAnimation(
                this, android.R.anim.fade_in, android.R.anim.fade_out
            )
            when (progress) {
                100 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent, options.toBundle())
                    finish()
                }
                else -> {
                    showProgress()
                }
            }
        }, delayTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        progressHandler?.removeCallbacksAndMessages(null)
    }
}