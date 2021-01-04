package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.posting.vacancy.JobVacancyPostFragment
import haina.ecommerce.view.HistorySellFragment

class TabAdapter (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Vacancy", "News")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> JobVacancyPostFragment()
        1 -> HistorySellFragment()
        else -> JobVacancyPostFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}