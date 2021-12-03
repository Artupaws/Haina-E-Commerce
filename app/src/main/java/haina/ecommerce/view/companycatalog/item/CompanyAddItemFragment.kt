package haina.ecommerce.view.companycatalog.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentCompanyAddItemBinding


class CompanyAddItemFragment : Fragment() {

    private lateinit var _binding : FragmentCompanyAddItemBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompanyAddItemBinding.inflate(inflater, container, false)

        binding.btnAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_companyAddItemFragment_to_companyCatalogItemsFragment)
        }

        return binding.root
    }


}