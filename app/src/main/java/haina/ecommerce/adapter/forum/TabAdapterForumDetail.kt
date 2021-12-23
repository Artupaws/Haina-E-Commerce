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
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish

class TabAdapterForumDetail (fm: FragmentManager, behavior: Int,idForum:Int,data:SubforumEngagement,context: Context) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf(context.getString(R.string.tab_title_post), context.getString(R.string.tab_title_about), context.getString(R.string.tab_title_manage))
    private var tabCount:Int = 2

    var id:Int? =null
    var listPost:ForumListPostFragment = ForumListPostFragment()
    var about:ForumAboutFragment = ForumAboutFragment()
    var manage:ForumManageFragment = ForumManageFragment()

    init {
        id = idForum
        val bundle:Bundle = Bundle()
        bundle.putInt("idForum", id!!)
        bundle.putParcelable("dataEngagement", data)

        listPost.arguments = bundle

        about.arguments = bundle
        manage.arguments = bundle
        if(data.role=="mod"){
            tabCount = 3
        }
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> listPost
        1 -> about
        2 -> manage
        else -> FragmentTransactionUnfinish()
    }

    override fun getCount(): Int = tabCount

    override fun getPageTitle(position: Int): CharSequence = tabName[position]

}