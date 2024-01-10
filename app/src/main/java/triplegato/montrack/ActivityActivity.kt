package triplegato.montrack

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import triplegato.montrack.databinding.ActivityActivityBinding

class ActivityActivity : AppCompatActivity() {
    private lateinit var binding : ActivityActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.USAFA_Blue)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = false
            }
        }

        binding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.USAFA_Blue))
        binding.tabLayout.tabTextColors = ContextCompat.getColorStateList(this, R.color.white)

        val numberOfTabs = 2

        binding.tabLayout.tabMode = TabLayout.MODE_FIXED

        binding.tabLayout.isInlineLabel = true

        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs)
        binding.tabsViewpager.adapter = adapter

        binding.tabsViewpager.isUserInputEnabled = true

        TabLayoutMediator(binding.tabLayout, binding.tabsViewpager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "pengeluaran"
                }
                1 -> {
                    tab.text = "pemasukkan"
                }
            }
        }.attach()

        setCustomTabTitles()
    }
    private fun setCustomTabTitles() {
        val vg = binding.tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount

        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup

            val tabChildCount = vgTab.childCount

            for (i in 0 until tabChildCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {

                    val font = ResourcesCompat.getFont(this, R.font.work_sans_medium)
                    tabViewChild.typeface = font
                }
            }
        }
    }
}