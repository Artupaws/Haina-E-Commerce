package haina.ecommerce.view.companycatalog.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.company.CompanyOverviewPagerAdapter
import haina.ecommerce.adapter.companycatalog.company.CompanyPromotedItemsAdapter
import haina.ecommerce.databinding.FragmentCompanyOverviewBinding


class CompanyOverviewFragment : Fragment(), CompanyPromotedItemsAdapter.ItemAdapterCallback {

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.company_data,
            R.string.catalogs
        )
    }

    private lateinit var _binding : FragmentCompanyOverviewBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompanyOverviewBinding.inflate(inflater, container, false)

        Glide.with(binding.ivCompanyLogo.context).load(R.drawable.skeleton_image).into(binding.ivCompanyLogo)

        binding.tvCompanyOverviewName.text = "PT Maju Mundur Cantik"

        val adapter = CompanyPromotedItemsAdapter(this)
        binding.rvCompanyPromotedItems.adapter = adapter

        val overviewTabAdapter = CompanyOverviewPagerAdapter(this)
        binding.vpCompany.adapter = overviewTabAdapter

        binding.toolbarDashboardCompany

        TabLayoutMediator(binding.tabCompany, binding.vpCompany){
            tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        return binding.root
    }

    override fun promotedItemOnClick() {
        findNavController().navigate(R.id.action_companyOverviewFragment_to_companyItemDetailFragment)
    }


}