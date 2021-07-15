package haina.ecommerce.view.property.fragmentdetailproperty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterListFacilityShow
import haina.ecommerce.databinding.FragmentDetailPropertyBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.property.DataShowProperty

class DetailPropertyFragment : Fragment(), View.OnClickListener {

    private lateinit var _binding:FragmentDetailPropertyBinding
    private val binding get() = _binding
    private var  imagesListener : ImageListener? = null
    private lateinit var listParams: ArrayList<String>
    private val helper: Helper = Helper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnBuy.setOnClickListener(this)
        binding.btnRent.setOnClickListener(this)
        listParams = ArrayList()
        val dataProperty = arguments?.getParcelable<DataShowProperty>("dataProperty")
        if (dataProperty?.images != null){
            for (i in dataProperty.images) {
                i?.path?.let { listParams.add(it) }
                Log.d("listImageProperty", listParams.toString())
                binding.vpImageProperty.pageCount = listParams.size
                imagesListener = ImageListener { position, imageView ->
                    Glide.with(this).load(listParams[position]).placeholder(R.drawable.ps5).into(imageView)
                }
                binding.vpImageProperty.setImageListener(imagesListener)
                binding.vpImageProperty.setImageListener(imagesListener)
            }
        }
        val address = "${dataProperty?.city?.province}, ${dataProperty?.city?.nameCity}"
        binding.tvLocation.text = address
        binding.tvNameProperty.text = dataProperty?.title
        binding.toolbarDetailProperty.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        dataProperty?.let { showData(it) }

    }

    private fun showData(data:DataShowProperty){
        if (data.rentalPrice.toString() == "0" && data.sellingPrice.toString() != "0"){
            binding.tvPriceSell.text = helper.convertToFormatMoneyIDRFilter(data.sellingPrice.toString())
            binding.linearPriceRent.visibility = View.GONE
        } else if (data.rentalPrice.toString() != "0" && data.sellingPrice.toString() == "0"){
            binding.tvPriceRent.text = helper.convertToFormatMoneyIDRFilter(data.rentalPrice.toString())
            binding.linearPriceSell.visibility = View.GONE
        } else {
            val priceSell = helper.convertToFormatMoneyIDRFilter(data.sellingPrice.toString())
            val priceRent = helper.convertToFormatMoneyIDRFilter(data.rentalPrice.toString())
            binding.tvPriceSell.text =  priceSell
            binding.tvPriceRent.text =  priceRent
        }

        binding.tvPropertyType.text = data.propertyType
        binding.tvBuildingArea.text = data.buildingArea
        binding.tvLandArea.text = data.landArea
        binding.tvBedroom.text = data.bedroom.toString()
        binding.tvBathroom.text= data.bathroom.toString()
        binding.tvFloor.text = data.floorLevel.toString()
        binding.rvFacility.apply {
            adapter = AdapterListFacilityShow(requireActivity(), data.facilities)
            layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.tvCertificate.text = data.certificateType
        binding.tvAddress.text = data.address
        binding.tvDescription.text = data.address
        binding.tvNameSeller.text = data.owner?.fullname
        val memberSince = "Member since ${data.owner?.createdAt?.substring(0,10)}"
        binding.tvSinceMember.text = memberSince
        Glide.with(requireActivity()).load("https://hainaservice.com/storage/${data.owner?.photo}").into(binding.ivSeller)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_buy -> {
                Toast.makeText(requireActivity(), "Buy", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_rent -> {
                Toast.makeText(requireActivity(), "Rent", Toast.LENGTH_SHORT).show()
            }
        }
    }

}