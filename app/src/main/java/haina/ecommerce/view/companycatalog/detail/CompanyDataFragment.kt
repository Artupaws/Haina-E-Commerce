package haina.ecommerce.view.companycatalog.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.company.CompanyDataMediaAdapter
import haina.ecommerce.adapter.companycatalog.company.CompanyPromotedItemsAdapter
import haina.ecommerce.databinding.FragmentCompanyDataBinding


class CompanyDataFragment : Fragment() {

    private lateinit var _binding : FragmentCompanyDataBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompanyDataBinding.inflate(inflater, container, false)

        binding.tvCompanyYear.text = "2010"

        val media_adapter = CompanyDataMediaAdapter()
        binding.rvCompanyMedia.adapter = media_adapter

        return binding.root
    }


}