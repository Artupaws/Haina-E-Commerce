package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.posting.vacancy.JobVacancyPostFragment
import haina.ecommerce.view.history.historysubmitapplication.HistorySubmitJobFragment

class TabAdapterPosting (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Job Vacancy")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> JobVacancyPostFragment()
        else -> JobVacancyPostFragment()
    }

    override fun getCount(): Int = 1

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}