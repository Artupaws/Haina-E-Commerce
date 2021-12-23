package haina.ecommerce.adapter.forum

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.R
import haina.ecommerce.view.forum.tab.showforum.ShowForumFragment
import haina.ecommerce.view.forum.tab.myforum.MySubforumFragment
import haina.ecommerce.view.forum.tab.mypost.MyPostFragment
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish

class TabAdapterForum (fm: FragmentManager, behavior: Int, context: Context?) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf(context?.getString(R.string.home_forum)!!, context?.getString(R.string.my_forum))

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> ShowForumFragment()
        1 -> MySubforumFragment()
//        2 -> MyPostFragment()
        else -> MySubforumFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = tabName[position]

}