package academy.bangkit.brewtopia.main

import academy.bangkit.brewtopia.main.dev.DevFragment
import academy.bangkit.brewtopia.main.home.HomeFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = DevFragment("fav")
            2 -> fragment = DevFragment("chat")
            3 -> fragment = DevFragment("scan")
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 4
    }
}