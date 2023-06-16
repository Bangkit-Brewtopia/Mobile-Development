package academy.bangkit.brewtopia.main

import academy.bangkit.brewtopia.main.chat.ChatFragment
import academy.bangkit.brewtopia.main.dev.DevFragment
import academy.bangkit.brewtopia.main.favorite.FavoriteFragment
import academy.bangkit.brewtopia.main.home.HomeFragment
import academy.bangkit.brewtopia.main.scan.ScanFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = FavoriteFragment()
            2 -> fragment = ChatFragment()
            3 -> fragment = ScanFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 4
    }
}