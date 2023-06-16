package academy.bangkit.brewtopia.main.articles

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.account.ProfileActivity
import academy.bangkit.brewtopia.data.local.Articles
import academy.bangkit.brewtopia.main.MainActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext

class DetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.hide()

        val data = intent.getParcelableExtra<Articles>("article")
        val banner = findViewById<ImageView>(R.id.iv_banner_detail)
        val title = findViewById<TextView>(R.id.tv_title_detail)
        val author = findViewById<TextView>(R.id.tv_author_detail)
        val content = findViewById<TextView>(R.id.tv_content_detail)

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener() {
            startActivity(Intent(this, MainActivity::class.java))
        }

        if (data != null) {
            banner.setImageResource(data.banner)
            title.text = data.title
            author.text = "Created By : ${data.author}"
            content.text = data.content
        }
    }
}