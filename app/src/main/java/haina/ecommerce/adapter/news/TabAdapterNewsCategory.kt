package haina.ecommerce.adapter.news

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import haina.ecommerce.model.news.NewsCategory
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.forum.tab.showforum.ShowForumFragment
import haina.ecommerce.view.forum.tab.myforum.MySubforumFragment
import haina.ecommerce.view.forum.tab.mypost.MyPostFragment
import haina.ecommerce.view.history.historytransaction.unfinish.FragmentTransactionUnfinish
import haina.ecommerce.view.news.fragment.NewsCategoryFragment

class TabAdapterNewsCategory (fm: FragmentManager, behavior: Int, val data: List<NewsCategory?>, context: Context) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: MutableList<String> = mutableListOf()
    private val fragmentList:MutableList<NewsCategoryFragment> = mutableListOf()
    private var sharedPreferenceHelper: SharedPreferenceHelper = SharedPreferenceHelper(context)

    init {
        for (i in data) {
            if(sharedPreferenceHelper.getValueString(Constants.LANGUAGE_APP) == "en"){
                tabName.add(i!!.name!!)
            }
            else{
                tabName.add(i!!.nameZh!!)
            }
            fragmentList.add(NewsCategoryFragment(i!!))
        }
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence = tabName[position]

}