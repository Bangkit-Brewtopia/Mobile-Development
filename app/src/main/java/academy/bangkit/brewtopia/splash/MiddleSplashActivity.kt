package academy.bangkit.brewtopia.splash

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.databinding.ActivitySplashMiddleBinding
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class MiddleSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashMiddleBinding
    private val delayTime: Long = 50
    private var progressHandler: Handler? = null
    private var waitNext = true

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashMiddleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        showProgress()

        val options = ActivityOptions.makeCustomAnimation(
            this, android.R.anim.fade_in, android.R.anim.fade_out
        )

        val rightZoneView: View = findViewById(R.id.right_zone)
        rightZoneView.setOnClickListener {
            val intent = Intent(this, LastSplashActivity::class.java)
            startActivity(intent, options.toBundle())
            waitNext = false
            finish()
        }

        val leftZoneView: View = findViewById(R.id.left_zone)
        leftZoneView.setOnClickListener {
            val intent = Intent(this, FirstSplashActivity::class.java)
            startActivity(intent, options.toBundle())
            waitNext = false
            finish()
        }
    }

    private fun showProgress() {
        if (waitNext) {
            Handler(Looper.getMainLooper()).postDelayed({
                val progress = binding.loadingSplashMiddle.progress + 1
                binding.loadingSplashMiddle.progress = progress
                val options = ActivityOptions.makeCustomAnimation(
                    this, android.R.anim.fade_in, android.R.anim.fade_out
                )
                when (progress) {
                    100 -> {
                        val intent = Intent(this, LastSplashActivity::class.java)
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