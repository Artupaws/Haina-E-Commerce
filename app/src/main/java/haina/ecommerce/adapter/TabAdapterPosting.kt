package haina.ecommerce.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.R
import haina.ecommerce.view.history.historytransaction.cancel.FragmentTransactionCancel

class TabAdapterPosting(fm: FragmentManager, behavior: Int, context: Context?) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf(context?.getString(R.string.job_vacancy)!!)

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> FragmentTransactionCancel()
        else -> FragmentTransactionCancel()
    }

    override fun getCount(): Int = 1

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}