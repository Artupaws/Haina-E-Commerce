package haina.ecommerce.adapter.hotel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish
import haina.ecommerce.view.hotels.transactionhotel.finish.FinishHotelFragment
import haina.ecommerce.view.hotels.transactionhotel.fragment.CancelHotelFragment
import haina.ecommerce.view.hotels.transactionhotel.unfinish.UnfinishHotelFragment

class TabAdapterHistoryHotelTransaction (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Unfinish", "Finish", "Cancel")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> UnfinishHotelFragment()
        1 -> FinishHotelFragment()
        2 -> CancelHotelFragment()
        else -> FragmentTransactionUnfinish()
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence = tabName[position]
}