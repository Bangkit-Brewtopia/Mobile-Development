package academy.bangkit.brewtopia.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.databinding.ActivityAboutBinding
import academy.bangkit.brewtopia.data_developer.Developer
import academy.bangkit.brewtopia.data_developer.ListDeveloperAdapter
import academy.bangkit.brewtopia.main.MainActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    private lateinit var rvDeveloper: RecyclerView
    private val list = ArrayList<Developer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        rvDeveloper = binding.rvDeveloper
        rvDeveloper.setHasFixedSize(true)

        list.addAll(getListDeveloper())
        showRecyclerDev()
    }

    @SuppressLint("Recycle")
    private fun getListDeveloper(): ArrayList<Developer> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataLinkedin = resources.getStringArray(R.array.linkedin)
        val dataProfileDev = resources.obtainTypedArray(R.array.profile_dev)
        val listDeveloper = ArrayList<Developer>()
        for (i in dataName.indices) {
            val artis = Developer(dataName[i], dataProfileDev.getResourceId(i, -1), dataLinkedin[i])
            listDeveloper.add(artis)
        }
        return listDeveloper
    }

    private fun showRecyclerDev() {
        rvDeveloper.layoutManager = LinearLayoutManager(this)
        val listDeveloperAdapter = ListDeveloperAdapter(list)
        rvDeveloper.adapter = listDeveloperAdapter

        listDeveloperAdapter.setOnItemClickCallback(object :
            ListDeveloperAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Developer) {
                val linkedin = data.linkedin
                val moveIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedin))
                startActivity(moveIntent)
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}