package haina.ecommerce.adapter.forum

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.view.forum.tab.forumabout.ForumAboutFragment
import haina.ecommerce.view.forum.tab.forumpost.ForumListPostFragment
import haina.ecommerce.view.forum.tab.forumpost.ForumListPostPresenter
import haina.ecommerce.view.forum.tab.showforum.ShowForumFragment
import haina.ecommerce.view.forum.tab.myforum.MySubforumFragment
import haina.ecommerce.view.forum.tab.mypost.MyPostFragment
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish

class TabAdapterForumDetail (fm: FragmentManager, behavior: Int,idForum:Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Post", "About", "Manage")

    var id:Int? =null
    var listPost:ForumListPostFragment = ForumListPostFragment()
    var about:ForumAboutFragment = ForumAboutFragment()

    init {
        id = idForum
        val bundle:Bundle = Bundle()
        bundle.putInt("idForum", id!!)

        listPost.arguments = bundle

        about.arguments = bundle
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> listPost
        1 -> about
        2 -> MyPostFragment()
        else -> FragmentTransactionUnfinish()
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence = tabName[position]

}