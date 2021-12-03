package haina.ecommerce.view.companycatalog.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.items.CompanyItemMediaAdapter
import haina.ecommerce.databinding.FragmentCompanyItemDetailBinding


class CompanyItemDetailFragment : Fragment(), View.OnClickListener {

    private lateinit var _binding : FragmentCompanyItemDetailBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCompanyItemDetailBinding.inflate(inflater, container, false)

        Glide.with(this).load(R.drawable.skeleton_image).into(binding.ivCompanyLogo)

        binding.tvItemName.text = "PS5 Base Asli Mulus"
        binding.tvItemDesc.text = "Barang mahal awas pecah\nTidak menerima komplain jika barang rusak oleh kurir\nHanya tersedia warna hitam loh"
        binding.tvPrice.text = "Rp 4.900.000"

        binding.tvCompanyName.text = "PT Maju Mundur Cantik"
        binding.tvItemCatalog.text = "Gaming"
        binding.tvItemCategory.text = "Electronics"

        binding.ivCompanyLogo.setOnClickListener(this)
        binding.tvCompanyName.setOnClickListener(this)

        val adapter = CompanyItemMediaAdapter()

        binding.rvItemMedia.adapter = adapter
        binding.rvItemMedia.layoutManager = GridLayoutManager(context, 3)

        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.iv_company_logo, R.id.tv_company_name -> {
                findNavController().navigate(R.id.action_companyItemDetailFragment_to_companyOverviewFragment)
            }
        }
    }


}