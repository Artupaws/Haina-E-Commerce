package haina.ecommerce.adapter.property

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.R
import haina.ecommerce.view.history.historymyproperty.ads.MyAdsFragment
import haina.ecommerce.view.history.historymyproperty.saved.SavedFragment

class TabAdapterMyProperty (fm: FragmentManager, behavior: Int, context: Context) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf(context.getString(R.string.my_ads), context.getString(
            R.string.saved_property))

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> MyAdsFragment()
        1 -> SavedFragment()
        else -> MyAdsFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = tabName[position]
}