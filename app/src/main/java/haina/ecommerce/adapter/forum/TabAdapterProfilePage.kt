package haina.ecommerce.adapter.forum

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.R
import haina.ecommerce.model.forum.SubforumEngagement
import haina.ecommerce.view.forum.tab.forumabout.ForumAboutFragment
import haina.ecommerce.view.forum.tab.forummanage.ForumManageFragment
import haina.ecommerce.view.forum.tab.forumpost.ForumListPostFragment
import haina.ecommerce.view.forum.tab.forumpost.ForumListPostPresenter
import haina.ecommerce.view.forum.tab.showforum.ShowForumFragment
import haina.ecommerce.view.forum.tab.myforum.MySubforumFragment
import haina.ecommerce.view.forum.tab.mypost.MyPostFragment
import haina.ecommerce.view.forum.tab.profilecomment.ProfileCommentListFragment
import haina.ecommerce.view.forum.tab.profilecomment.ProfileCommentListPresenter
import haina.ecommerce.view.forum.tab.profilepost.ProfilePostListFragment
import haina.ecommerce.view.forum.tab.profilepost.ProfilePostListPresenter
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish

class TabAdapterProfilePage (fm: FragmentManager, behavior: Int,idUser:Int, context: Context) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf(context.getString(R.string.tab_title_post), context.getString(R.string.comments))
    private var tabCount:Int = 2

    var id:Int? =null
    var listPost:ProfilePostListFragment = ProfilePostListFragment()
    var about:ProfileCommentListFragment = ProfileCommentListFragment()

    init {
        id = idUser
        val bundle:Bundle = Bundle()
        bundle.putInt("idUser", id!!)

        listPost.arguments = bundle

        about.arguments = bundle
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> listPost
        1 -> about
        else -> FragmentTransactionUnfinish()
    }

    override fun getCount(): Int = tabCount

    override fun getPageTitle(position: Int): CharSequence = tabName[position]

}