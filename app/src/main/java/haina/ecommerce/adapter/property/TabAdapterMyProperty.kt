package haina.ecommerce.adapter.property

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.history.historyjobvacancy.historyinterviewapplicant.HistoryInterviewFragment
import haina.ecommerce.view.history.historymyproperty.ads.MyAdsFragment
import haina.ecommerce.view.history.historymyproperty.saved.SavedFragment
import haina.ecommerce.view.history.historysubmitapplication.HistorySubmitJobFragment
import haina.ecommerce.view.history.historytransaction.cancel.FragmentTransactionCancel
import haina.ecommerce.view.history.historytransaction.finish.FragmentTransactionFinish
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish

class TabAdapterMyProperty (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("My Ads", "Saved")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> MyAdsFragment()
        1 -> SavedFragment()
        else -> MyAdsFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = tabName[position]
}