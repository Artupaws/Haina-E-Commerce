package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.HistoryBillFragment
import haina.ecommerce.view.HistoryBuyFragment
import haina.ecommerce.view.HistorySellFragment

class TabAdapter (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Buying", "Selling", "Bill")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> HistoryBuyFragment()
        1 -> HistorySellFragment()
        2 -> HistoryBillFragment()
        else -> HistoryBuyFragment()
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}