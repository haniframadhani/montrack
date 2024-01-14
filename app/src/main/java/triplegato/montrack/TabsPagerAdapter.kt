package triplegato.montrack

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return PengeluaranFragment()
            1 -> return PemasukkanFragment()
            else -> return PemasukkanFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}