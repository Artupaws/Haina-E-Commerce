package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.history.historytransaction.cancel.FragmentTransactionCancel
import haina.ecommerce.view.history.historytransaction.finish.FragmentTransactionFinish
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish

class TabAdapterHistoryTransaction (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Unfinish", "Finish", "Cancel")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> FragmentTransactionUnfinish()
        1 -> FragmentTransactionFinish()
        2 -> FragmentTransactionCancel()
        else -> FragmentTransactionUnfinish()
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence = tabName[position]
}