package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.history.historytransaction.cancel.FragmentTransactionCancel
import haina.ecommerce.view.posting.newvacancy.NewPostVacancyActivity

class TabAdapterPosting (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Job Vacancy")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> FragmentTransactionCancel()
        else -> FragmentTransactionCancel()
    }

    override fun getCount(): Int = 1

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}