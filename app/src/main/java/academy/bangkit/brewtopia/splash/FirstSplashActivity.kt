package academy.bangkit.brewtopia.splash

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.databinding.ActivitySplashFirstBinding
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class FirstSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashFirstBinding
    private val delayTime: Long = 50
    private var progressHandler: Handler? = null
    private var waitNext = true

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashFirstBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        showProgress()

        val rightZoneView: View = findViewById(R.id.right_zone)
        val options = ActivityOptions.makeCustomAnimation(
            this, android.R.anim.fade_in, android.R.anim.fade_out
        )
        rightZoneView.setOnClickListener {
            val intent = Intent(this, MiddleSplashActivity::class.java)
            startActivity(intent, options.toBundle())
            waitNext = false
            finish()
        }
    }

    private fun showProgress() {
        if (waitNext) {
            Handler(Looper.getMainLooper()).postDelayed({
                val progress = binding.loadingSplashFirst.progress + 1
                binding.loadingSplashFirst.progress = progress
                val options = ActivityOptions.makeCustomAnimation(
                    this, android.R.anim.fade_in, android.R.anim.fade_out
                )
                when (progress) {
                    100 -> {
                        val intent = Intent(this, MiddleSplashActivity::class.java)
                        startActivity(intent, options.toBundle())
                        finish()
                    }

                    else -> {
                        showProgress()
                    }
                }
            }, delayTime)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        progressHandler?.removeCallbacksAndMessages(null)
    }
}