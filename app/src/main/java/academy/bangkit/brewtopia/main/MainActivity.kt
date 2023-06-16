package academy.bangkit.brewtopia.main

import academy.bangkit.brewtopia.R
import academy.bangkit.brewtopia.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navBar: ChipNavigationBar
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        navBar = binding.navBar
        viewPager = binding.ViewPager2
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        navBar.setOnItemSelectedListener { id ->
            val position = getPositionFromMenuItemId(id)
            if (position != -1) {
                viewPager.setCurrentItem(position, true)
            }
        }
    }

    fun navigateToChatFragment() {
        viewPager.currentItem = 2
    }

    fun navigateToScanFragment() {
        viewPager.currentItem = 3
    }

    private fun getPositionFromMenuItemId(menuItemId: Int): Int {
        return when (menuItemId) {
            R.id.menu_home -> 0
            R.id.menu_favorite -> 1
            R.id.menu_chat -> 2
            R.id.menu_scan -> 3
            else -> -1
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}