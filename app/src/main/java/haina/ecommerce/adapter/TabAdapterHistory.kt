package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.history.historyshortlistapplicant.HistorySortListActivity
import haina.ecommerce.view.history.historysubmitapplication.HistorySubmitJobFragment

class TabAdapterHistory (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Application", "Shortlist Applicant")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> HistorySubmitJobFragment()
        1 -> HistorySortListActivity()
        else -> HistorySubmitJobFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}