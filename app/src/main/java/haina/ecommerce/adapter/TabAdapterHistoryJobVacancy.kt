package haina.ecommerce.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.history.historyjobvacancy.historyacceptapplicant.HistoryAcceptFragment
import haina.ecommerce.view.history.historyjobvacancy.historyinterviewapplicant.HistoryInterviewFragment
import haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant.HistoryShortListFragment
import haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant.HistorySubmitFragment
import haina.ecommerce.view.history.historysubmitapplication.HistorySubmitJobActivity

class TabAdapterHistoryJobVacancy (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Application", "Shortlist Applicant", "Interview Applicant", "Accept Applicant")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> HistorySubmitFragment()
        1 -> HistoryShortListFragment()
        2 -> HistoryInterviewFragment()
        3 -> HistoryAcceptFragment()
        else -> HistorySubmitFragment()
    }

    override fun getCount(): Int = 4

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}