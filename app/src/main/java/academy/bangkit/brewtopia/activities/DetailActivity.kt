package academy.bangkit.brewtopia.activities

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.data_artikel.Articles
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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

        if (data != null) {
            banner.setImageResource(data.banner)
            title.text = data.title
            author.text = "created by: ${data.author}"
            content.text = data.content
        }
    }
}