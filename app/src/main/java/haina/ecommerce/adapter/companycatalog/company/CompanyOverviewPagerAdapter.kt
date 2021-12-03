package haina.ecommerce.adapter.companycatalog.company

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import haina.ecommerce.view.companycatalog.detail.CompanyCatalogListFragment
import haina.ecommerce.view.companycatalog.detail.CompanyDataFragment

class CompanyOverviewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when(position){
            0 -> fragment = CompanyDataFragment()
            1 -> fragment = CompanyCatalogListFragment()
        }

        return fragment as Fragment
    }
}