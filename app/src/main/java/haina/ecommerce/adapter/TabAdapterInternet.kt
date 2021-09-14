package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant.HistoryShortListPresenter
import haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant.HistorySubmitFragment
import haina.ecommerce.view.history.historysubmitapplication.HistorySubmitJobActivity
import haina.ecommerce.view.topup.paketdata.PaketDataFragment
import haina.ecommerce.view.topup.pulsa.PulsaFragment

class TabAdapterInternet (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Pulsa", "Paket Data")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> PulsaFragment()
        1 -> PaketDataFragment()
        else -> HistorySubmitFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}