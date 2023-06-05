package academy.bangkit.brewtopia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DummyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DummyAdapter(getDummyData())
        recyclerView.adapter = adapter
    }

    private fun getDummyData(): List<String> {
        val data = ArrayList<String>()
        for (i in 1..20) {
            data.add("Data Dummy Article $i")
        }
        return data
    }
}