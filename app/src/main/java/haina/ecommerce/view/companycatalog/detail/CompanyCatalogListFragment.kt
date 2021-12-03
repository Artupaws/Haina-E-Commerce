package haina.ecommerce.view.companycatalog.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.company.CompanyCatalogListAdapter
import haina.ecommerce.databinding.FragmentCompanyCatalogsBinding


class CompanyCatalogListFragment : Fragment(), CompanyCatalogListAdapter.ItemAdapterCallback {

    private lateinit var _binding : FragmentCompanyCatalogsBinding
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompanyCatalogsBinding.inflate(inflater, container, false)

        val adapter = CompanyCatalogListAdapter(this)
        binding.rvCatalogList.adapter = adapter

        return binding.root
    }

    override fun catalogOnClick() {
        findNavController().navigate(R.id.action_companyOverviewFragment_to_companyCatalogItemsFragment)
    }


}