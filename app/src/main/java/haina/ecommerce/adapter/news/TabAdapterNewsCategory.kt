package haina.ecommerce.adapter.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.model.news.NewsCategory
import haina.ecommerce.view.forum.tab.showforum.ShowForumFragment
import haina.ecommerce.view.forum.tab.myforum.MySubforumFragment
import haina.ecommerce.view.forum.tab.mypost.MyPostFragment
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish
import haina.ecommerce.view.news.fragment.NewsCategoryFragment

class TabAdapterNewsCategory (fm: FragmentManager, behavior: Int, val data: List<NewsCategory?>) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: MutableList<String> = mutableListOf()
    private val fragmentList:MutableList<NewsCategoryFragment> = mutableListOf()

    init {
        for (i in data) {
            tabName.add(i!!.name!!)
            fragmentList.add(NewsCategoryFragment(i!!))
        }
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence = tabName[position]

}