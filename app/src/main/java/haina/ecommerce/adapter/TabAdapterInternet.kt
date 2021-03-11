package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.history.historyacceptapplicant.HistoryAcceptFragment
import haina.ecommerce.view.history.historyinterviewapplicant.HistoryInterviewFragment
import haina.ecommerce.view.history.historyshortlistapplicant.HistoryShortListFragment
import haina.ecommerce.view.history.historysubmitapplication.HistorySubmitJobFragment
import haina.ecommerce.view.internet.paketdata.PaketDataFragment
import haina.ecommerce.view.internet.pulsa.PulsaFragment

class TabAdapterInternet (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Pulsa", "Paket Data")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> PulsaFragment()
        1 -> PaketDataFragment()
        else -> HistorySubmitJobFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}