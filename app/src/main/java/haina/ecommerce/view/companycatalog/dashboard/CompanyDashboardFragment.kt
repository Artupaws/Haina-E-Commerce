package haina.ecommerce.view.companycatalog.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.CompanyDashboardAdapter
import haina.ecommerce.databinding.FragmentCompanyDashboardBinding


class CompanyDashboardFragment : Fragment() {

    private lateinit var _binding: FragmentCompanyDashboardBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompanyDashboardBinding.inflate(inflater, container, false)

        val adapter = CompanyDashboardAdapter()
        binding.rvCompanyCategories.adapter = adapter

        binding.cardView6.setOnClickListener {
            findNavController().navigate(R.id.action_companyDashboardFragment_to_companyGlobalSearchFragment)
        }

        adapter.setOnItemCallback(object: CompanyDashboardAdapter.ItemAdapterCallback{
            override fun categoryOnClick() {
                findNavController().navigate(R.id.action_companyDashboardFragment_to_companyCatalogItemsFragment)
            }
        })

        return binding.root
    }

}