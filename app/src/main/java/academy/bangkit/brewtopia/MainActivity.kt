package academy.bangkit.brewtopia

import academy.bangkit.brewtopia.databinding.ActivityMainBinding
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

    private fun getPositionFromMenuItemId(menuItemId: Int): Int {
        return when (menuItemId) {
            R.id.menu_home -> 0
            R.id.menu_favorite -> 1
            R.id.menu_chat -> 2
            R.id.menu_scan -> 3
            else -> -1
        }
    }
}