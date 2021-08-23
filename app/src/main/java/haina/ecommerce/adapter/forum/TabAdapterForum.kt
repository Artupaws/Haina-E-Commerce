package haina.ecommerce.adapter.forum

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.forum.tab.showforum.ShowForumFragment
import haina.ecommerce.view.forum.tab.myforum.MySubforumFragment
import haina.ecommerce.view.forum.tab.mypost.MyPostFragment
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish

class TabAdapterForum (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Threads", "MyForum", "MyPost")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> ShowForumFragment()
        1 -> MySubforumFragment()
        2 -> MyPostFragment()
        else -> FragmentTransactionUnfinish()
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence = tabName[position]
}