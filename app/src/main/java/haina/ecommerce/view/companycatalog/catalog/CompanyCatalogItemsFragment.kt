package haina.ecommerce.view.companycatalog.catalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.items.CompanyCatalogItemsAdapter
import haina.ecommerce.databinding.FragmentCompanyCatalogItemsBinding
import timber.log.Timber


class CompanyCatalogItemsFragment : Fragment(), CompanyCatalogItemsAdapter.ItemAdapterCallback {

    private lateinit var _binding: FragmentCompanyCatalogItemsBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompanyCatalogItemsBinding.inflate(inflater, container, false)

        val adapter = CompanyCatalogItemsAdapter(this)
        binding.rvItemList.adapter = adapter

        binding.tvAddItems.setOnClickListener {
            findNavController().navigate(R.id.action_companyCatalogItemsFragment_to_companyAddItemFragment)
        }

        return binding.root
    }

    override fun itemOnClick() {
        findNavController().navigate(R.id.action_companyCatalogItemsFragment_to_companyItemDetailFragment)
    }


}