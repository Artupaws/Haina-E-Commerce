package haina.ecommerce.view.property.fragmentinputdata

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterListAmountRoom
import haina.ecommerce.adapter.property.AdapterListCity
import haina.ecommerce.adapter.property.AdapterListFacility
import haina.ecommerce.adapter.property.AdapterListProvince
import haina.ecommerce.databinding.FragmentInputDataPropertyBinding
import haina.ecommerce.helper.Helper.changeFormatMoneyToValue
import haina.ecommerce.helper.NumberTextWatcher
import haina.ecommerce.model.property.DataCity
import haina.ecommerce.model.property.DataFacilitiesProperty
import haina.ecommerce.model.property.DataProvince
import haina.ecommerce.model.property.RequestDataProperty
import haina.ecommerce.view.property.ShowPropertyActivity
import timber.log.Timber
import java.util.*

class InputDataPropertyFragment : Fragment(), InputDataPropertyContract.View,
    AdapterListFacility.ItemAdapterCallback, AdapterListProvince.ItemAdapterCallback,
AdapterListCity.ItemAdapterCallback, View.OnClickListener, AdapterListAmountRoom.ItemAdapterCallback{

    private lateinit var _binding :FragmentInputDataPropertyBinding
    private val binding get() = _binding
    private var progressDialog:Dialog? = null
    private var popupProvince:Dialog? = null
    private var popupFloor:Dialog? = null
    private var popupCity:Dialog? = null
    private var idProvince:Int = 0
    private var idCity:Int = 0
    private lateinit var presenter: InputDataPropertyPresenter
    private var listFacility = ArrayList<Int>()
    private var amountRoom = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
    private var amountFloor = listOf<Int>()
    private var propertyType:String = ""
    private var typePosting:String = ""
    //
    private var radioTypePostingId: Int = 0
    //
    private var typeCertificate:String = ""
    private var typeAmountRoom:String = ""
    private var isEmptyTypeProperty = true
    private var isEmptyTypePosting = true
    private var isEmptyBuildingArea = true
    private var isEmptyLandArea = true
    private var isEmptyBedRoom = true
    private var isEmptyBathRoom = true
    private var isEmptyFloor = true
    private var isEmptyYear = true
    private var isEmptyFacility = true
    private var isEmptyTypeCertificate = true
    private var isEmptyTypeProvince = true
    private var isEmptyTypeCity = true
    private var isEmptyAddress = true
    private var isEmptyTitle = true
    private var isEmptyDescription = true
    private var isEmptyPriceSell = true
    private var isEmptyPriceRent = true
    private var stringFacilities:String =""
    private var dataRequest:RequestDataProperty? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInputDataPropertyBinding.inflate(inflater, container, false)
        presenter = InputDataPropertyPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar7.setNavigationOnClickListener {
            (requireActivity() as ShowPropertyActivity).onBackPressed()
        }
        for (i in 1 until 100){
            amountFloor = listOf(i)
        }
        binding.includeDataPropertyTop.etProvince.setOnClickListener(this)
        binding.includeDataPropertyTop.etCity.setOnClickListener(this)
        binding.includeDataPropertyTop.btnNext.setOnClickListener(this)
        binding.includeDataPropertyTop.etFloor.setOnClickListener(this)
        binding.includeDataPropertyTop.etBedroom.setOnClickListener(this)
        binding.includeDataPropertyTop.etBathroom.setOnClickListener(this)
        presenter.getFacilities()
        presenter.getProvince()
        radioGroup()
        popupDialogFloor(amountRoom)
        binding.includeDataPropertyTop.rbBoth.isChecked = true
        val locale = Locale("es", "IDR")
        val numDecs = 2 // Let's use 2 decimals
        val priceSell: TextWatcher = NumberTextWatcher(binding.includeDataPropertyTop.etSetPriceSell, locale, numDecs)
        val priceRent: TextWatcher = NumberTextWatcher(binding.includeDataPropertyTop.etSetPrice, locale, numDecs)
        binding.includeDataPropertyTop.etSetPrice.addTextChangedListener(priceRent)
        binding.includeDataPropertyTop.etSetPriceSell.addTextChangedListener(priceSell)
    }

    override fun onResume() {
        super.onResume()
        listFacility.clear()
        if (dataRequest?.typeProperty != null){
        when(dataRequest?.typeProperty){
            "apartment" -> {
                binding.includeDataPropertyTop.rbApartment.isChecked = true
            }
            "house" -> {
                binding.includeDataPropertyTop.rbHouse.isChecked = true
            }
            "warehouse" -> {
                binding.includeDataPropertyTop.rbWarehouse.isChecked = true
            }
            "office" -> {
                binding.includeDataPropertyTop.rbOffice.isChecked = true
            }
        }

        when(dataRequest?.typePosting){
            "sell" -> {
                binding.includeDataPropertyTop.rbForSell.isChecked = true
            }
            "rent" -> {
                binding.includeDataPropertyTop.rbForRent.isChecked = true
            }
            "both" -> {
                binding.includeDataPropertyTop.rbBoth.isChecked = true
            }
        }

        when(dataRequest?.typeCertificate){
            "SHM" -> {
                binding.includeDataPropertyTop.rbShm.isChecked = true
            }
            "HGB" -> {
                binding.includeDataPropertyTop.rbHgb.isChecked = true
            }
            "SHMSRS" -> {
                binding.includeDataPropertyTop.rbSrs.isChecked = true
            }
            "Girik" -> {
                binding.includeDataPropertyTop.rbGirik.isChecked = true
            }
        }

        } else {
            binding.includeDataPropertyTop.rbBoth.isChecked = true
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(requireActivity().getDrawable(android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogFloor(dataFloor: List<Int>) {
        popupFloor = Dialog(requireActivity())
        popupFloor?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupFloor?.setCancelable(true)
        popupFloor?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupFloor?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupFloor?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupFloor?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupFloor?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupFloor?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupFloor?.dismiss() }
            rvDestination?.apply {
                adapter = AdapterListAmountRoom(requireActivity(), dataFloor, this@InputDataPropertyFragment)
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
        searchView?.visibility= View.GONE
        title?.text = "Amount"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogProvince(dataProvince: List<DataProvince?>?) {
        popupProvince = Dialog(requireActivity())
        popupProvince?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupProvince?.setCancelable(true)
        popupProvince?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupProvince?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupProvince?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupProvince?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupProvince?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupProvince?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupProvince?.dismiss() }
        title?.text = getString(R.string.province)
        rvDestination?.apply {
            adapter = AdapterListProvince(requireActivity(), dataProvince, this@InputDataPropertyFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        searchView?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.isNotEmpty()!!){
                    (rvDestination?.adapter as AdapterListProvince).filter.filter(p0)
                    (rvDestination.adapter as AdapterListProvince).notifyDataSetChanged()
                } else {
                    (rvDestination?.adapter as AdapterListProvince).filter.filter("")
                }
                return true
            }
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogCity(dataCity: List<DataCity?>?) {
        popupCity = Dialog(requireActivity())
        popupCity?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupCity?.setCancelable(true)
        popupCity?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupCity?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupCity?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupCity?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupCity?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupCity?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupCity?.dismiss() }
        title?.text = getString(R.string.city_required)
        rvDestination?.apply {
            adapter = AdapterListCity(requireActivity(), dataCity, this@InputDataPropertyFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        searchView?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.isNotEmpty()!!){
                    (rvDestination?.adapter as AdapterListCity).filter.filter(p0)
                    (rvDestination.adapter as AdapterListCity).notifyDataSetChanged()
                } else {
                    (rvDestination?.adapter as AdapterListCity).filter.filter("")
                }
                return true
            }
        })
    }

    private fun radioGroup(){
        binding.includeDataPropertyTop.rdGroupPropertyType.setOnCheckedChangeListener { _, i ->
            binding.includeDataPropertyTop.tvTitleTypeProperty.error = null
            val radio: RadioButton = requireActivity().findViewById(i)
            radioButtonTypeProperty(radio)
        }
        binding.includeDataPropertyTop.rdGroupTypePosting.setOnCheckedChangeListener { _, i ->
            binding.includeDataPropertyTop.tvTitleTypePosting.error = null
            val radio: RadioButton = requireActivity().findViewById(i)
            radioButtonTypePosting(radio)
        }
        binding.includeDataPropertyTop.rdGroupCertificate.setOnCheckedChangeListener { _, i ->
            binding.includeDataPropertyTop.tvTitleTypeCertificate.error = null
            val radio :RadioButton = requireActivity().findViewById(i)
            radioButtonTypeCertificate(radio)
        }
    }

    private fun radioButtonTypePosting(view: View){
        val radio: RadioButton = requireActivity().findViewById(binding.includeDataPropertyTop.rdGroupTypePosting.checkedRadioButtonId)
        //typePosting = radio.id.toString()
        radioTypePostingId = radio.id
        when(radioTypePostingId){
            R.id.rb_for_sell -> {
                binding.includeDataPropertyTop.linearPriceRent.visibility = View.GONE
                binding.includeDataPropertyTop.linearPriceSell.visibility = View.VISIBLE
                binding.includeDataPropertyTop.linearTypeCertificate.visibility = View.VISIBLE
                typePosting = "sell"
            }
            R.id.rb_for_rent -> {
                binding.includeDataPropertyTop.linearPriceRent.visibility = View.VISIBLE
                binding.includeDataPropertyTop.linearPriceSell.visibility = View.GONE
                binding.includeDataPropertyTop.linearTypeCertificate.visibility = View.GONE
                typePosting = "rent"
            }
            R.id.rb_both -> {
                binding.includeDataPropertyTop.linearPriceRent.visibility = View.VISIBLE
                binding.includeDataPropertyTop.linearPriceSell.visibility = View.VISIBLE
                binding.includeDataPropertyTop.linearTypeCertificate.visibility = View.VISIBLE
                typePosting = "both"
            }
        }
    }

    private fun radioButtonTypeProperty(view: View){
        val radio: RadioButton = requireActivity().findViewById(binding.includeDataPropertyTop.rdGroupPropertyType.checkedRadioButtonId)
        propertyType = radio.text.toString()
        when(propertyType){
            "Warehouse" -> {
                binding.includeDataPropertyTop.linearBedroom.visibility = View.GONE
                binding.includeDataPropertyTop.linearLandArea.visibility = View.VISIBLE
                propertyType = "warehouse"
            }
            "Office" -> {
                binding.includeDataPropertyTop.linearLandArea.visibility = View.GONE
                binding.includeDataPropertyTop.linearBedroom.visibility = View.GONE
                propertyType = "office"
            }
            "Apartment" -> {
                binding.includeDataPropertyTop.linearLandArea.visibility = View.GONE
                propertyType = "apartment"
            }
            else -> {
                binding.includeDataPropertyTop.linearLandArea.visibility = View.VISIBLE
                binding.includeDataPropertyTop.linearBedroom.visibility = View.VISIBLE
                propertyType = "house"
            }
        }
    }

    private fun checkDataProperty(){
        var typePropertyParams = propertyType.lowercase()
        var typePostingParams = typePosting
        var buildingAreaParams = binding.includeDataPropertyTop.etBuildingArea.text.toString()
        var landAreaParams = binding.includeDataPropertyTop.etSurfaceArea.text.toString()
        var bedRoomParams = binding.includeDataPropertyTop.etBedroom.text.toString()
        var bathRoomParams = binding.includeDataPropertyTop.etBathroom.text.toString()
        var floorParams = binding.includeDataPropertyTop.etFloor.text.toString()
        var yearParams = binding.includeDataPropertyTop.etYear.text.toString()
        var facilityParams = stringFacilities
        var typeCertificateParams = typeCertificate
        var provinceParams = idProvince
        var cityParams = idCity
        var addressParams = binding.includeDataPropertyTop.etAddressProperty.text.toString()
        var titleParams = binding.includeDataPropertyTop.etTitleAds.text.toString()
        var descriptionParams = binding.includeDataPropertyTop.etDescriptionAds.text.toString()
        var priceSellParams = changeFormatMoneyToValue(binding.includeDataPropertyTop.etSetPriceSell.text.toString())
        var priceRentParams = changeFormatMoneyToValue(binding.includeDataPropertyTop.etSetPrice.text.toString())
        val condition:String = if (yearParams == Calendar.getInstance().get(Calendar.YEAR).toString()){
            "New"
        } else{
            "Existing"
        }
        if (typePropertyParams.isNullOrEmpty()){
            isEmptyTypeProperty = true
            binding.includeDataPropertyTop.tvTitleTypeProperty.error = getString(R.string.cant_empty)
        } else {
            isEmptyTypeProperty = false
            binding.includeDataPropertyTop.tvTitleTypeProperty.error = null
            typePropertyParams = propertyType
        }


        if (typePostingParams.isNullOrEmpty()){
            isEmptyTypePosting = true
            binding.includeDataPropertyTop.tvTitleTypePosting.error = getString(R.string.cant_empty)
        } else {
            isEmptyTypePosting = false
            binding.includeDataPropertyTop.tvTitleTypePosting.error = null
            typePostingParams = typePosting
        }


        if (buildingAreaParams.isNullOrEmpty()){
            isEmptyBuildingArea = true
            binding.includeDataPropertyTop.tvTitleBuildingArea.error = getString(R.string.cant_empty)
        } else {
            isEmptyBuildingArea = false
            binding.includeDataPropertyTop.tvTitleBuildingArea.error = null
            buildingAreaParams = binding.includeDataPropertyTop.etBuildingArea.text.toString()
        }

        if (landAreaParams.isNullOrEmpty()){
            when(propertyType){
                "Apartment" -> {
                    isEmptyLandArea = false
                    binding.includeDataPropertyTop.tvTitleLandArea.error = null
                }
                "Office" -> {
                    isEmptyLandArea = false
                    binding.includeDataPropertyTop.tvTitleLandArea.error = null
                }else -> {
                isEmptyLandArea = true
                binding.includeDataPropertyTop.tvTitleLandArea.error = getString(R.string.cant_empty) }
            }
        } else {
            isEmptyLandArea = false
            binding.includeDataPropertyTop.tvTitleLandArea.error = null
            landAreaParams = binding.includeDataPropertyTop.etSurfaceArea.text.toString()
        }

        if (bedRoomParams.isNullOrEmpty()){
            when(propertyType){
                "Warehouse" -> {
                    isEmptyBedRoom = false
                    binding.includeDataPropertyTop.tvTitleBedRoom.error = null
                }
                "Office" -> {
                    isEmptyBedRoom = false
                    binding.includeDataPropertyTop.tvTitleBedRoom.error = null
                }
                else -> {
                    isEmptyBedRoom = true
                binding.includeDataPropertyTop.tvTitleBedRoom.error = getString(R.string.cant_empty) }
            }
        } else {
            isEmptyBedRoom = false
            binding.includeDataPropertyTop.tvTitleBedRoom.error = null
            bedRoomParams = binding.includeDataPropertyTop.etBedroom.text.toString()
        }

        if (bathRoomParams.isNullOrEmpty()){
            isEmptyBathRoom = true
            binding.includeDataPropertyTop.tvTitleBathRoom.error = getString(R.string.cant_empty)
        } else {
            isEmptyBathRoom = false
            binding.includeDataPropertyTop.tvTitleBathRoom.error = null
            bathRoomParams = binding.includeDataPropertyTop.etBathroom.text.toString()
        }

        if (floorParams.isNullOrEmpty()){
            isEmptyFloor = true
            binding.includeDataPropertyTop.tvTitleFloor.error = getString(R.string.cant_empty)
        } else {
            isEmptyFloor = false
            binding.includeDataPropertyTop.tvTitleFloor.error = null
            floorParams = binding.includeDataPropertyTop.etFloor.text.toString()
        }

        if (yearParams.isNullOrEmpty()){
            isEmptyYear = true
            binding.includeDataPropertyTop.tvTitleYear.error = getString(R.string.cant_empty)
        } else {
            isEmptyYear = false
            binding.includeDataPropertyTop.tvTitleYear.error = null
            yearParams = binding.includeDataPropertyTop.etYear.text.toString()
        }

        if (facilityParams.isNullOrEmpty()){
            isEmptyFacility = true
            binding.includeDataPropertyTop.tvTitleFacility.error = getString(R.string.cant_empty)
//            isEmptyFacility = false
        } else {
            isEmptyFacility = false
            binding.includeDataPropertyTop.tvTitleFacility.error = null
//            facilityParams = listFacility
            facilityParams = stringFacilities
        }

        if (typeCertificateParams.isNullOrEmpty()){
            when(radioTypePostingId){
                R.id.rb_for_rent -> {
                    isEmptyTypeCertificate = false
                    binding.includeDataPropertyTop.tvTitleTypeCertificate.error = null
                    typeCertificateParams = "None"
                } else -> {
                isEmptyTypeCertificate = true
                binding.includeDataPropertyTop.tvTitleTypeCertificate.error = getString(R.string.cant_empty) }
            }
        } else {
            isEmptyTypeCertificate = false
            binding.includeDataPropertyTop.tvTitleTypeCertificate.error = null
            typeCertificateParams = typeCertificate
        }

        if (provinceParams == 0){
            isEmptyTypeProvince = true
            binding.includeDataPropertyTop.tvTitleProvince.error = getString(R.string.cant_empty)
        } else {
            isEmptyTypeProvince = false
            binding.includeDataPropertyTop.tvTitleProvince.error = null
            provinceParams = idProvince
        }

        if (cityParams == 0){
            isEmptyTypeCity = true
            binding.includeDataPropertyTop.tvTitleCity.error = getString(R.string.cant_empty)
        } else {
            isEmptyTypeCity = false
            binding.includeDataPropertyTop.tvTitleCity.error = null
            cityParams = idCity
        }

        if (addressParams.isNullOrEmpty()){
            isEmptyAddress = true
            binding.includeDataPropertyTop.tvTitleAddress.error = getString(R.string.cant_empty)
        } else {
            isEmptyAddress = false
            binding.includeDataPropertyTop.tvTitleAddress.error = null
            addressParams = binding.includeDataPropertyTop.etAddressProperty.text.toString()
        }

        if (titleParams.isNullOrEmpty()){
            isEmptyTitle = true
            binding.includeDataPropertyTop.tvTitleAds.error = getString(R.string.cant_empty)
        } else {
            isEmptyTitle = false
            binding.includeDataPropertyTop.tvTitleAds.error = null
            titleParams = binding.includeDataPropertyTop.etTitleAds.text.toString()
        }

        if (descriptionParams.isNullOrEmpty()){
            isEmptyDescription = true
            binding.includeDataPropertyTop.tvTitleDescription.error = getString(R.string.cant_empty)
        } else {
            isEmptyDescription = false
            binding.includeDataPropertyTop.tvTitleDescription.error = null
            descriptionParams = binding.includeDataPropertyTop.etDescriptionAds.text.toString()
        }

        if (priceRentParams.isEmpty()){
            when(radioTypePostingId){
                R.id.rb_for_sell -> {
                    isEmptyPriceRent = false
                    binding.includeDataPropertyTop.tvTitlePriceRent.error = null
                } else -> {
                    isEmptyPriceRent = true
                    binding.includeDataPropertyTop.tvTitlePriceRent.error = getString(R.string.cant_empty)
                }
            }
        } else {
            isEmptyPriceRent = false
            binding.includeDataPropertyTop.tvTitlePriceRent.error = null
            priceRentParams = binding.includeDataPropertyTop.etSetPrice.text.toString()
        }

        if (priceSellParams.isNullOrEmpty()){
            when(radioTypePostingId){
                R.id.rb_for_rent -> {
                    isEmptyPriceSell = false
                    binding.includeDataPropertyTop.tvTitlePriceSell.error = null
                } else -> {
                isEmptyPriceSell = true
                binding.includeDataPropertyTop.tvTitlePriceSell.error = getString(R.string.cant_empty) }
            }
        } else {
            isEmptyPriceSell = false
            binding.includeDataPropertyTop.tvTitlePriceSell.error = null
            priceSellParams = binding.includeDataPropertyTop.etSetPriceSell.text.toString()
        }

        if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyLandArea && !isEmptyBedRoom && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeCertificate && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceSell
            && typePropertyParams == "house" && typePostingParams == "sell"){
                dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(),
                landAreaParams.toInt(), bedRoomParams.toInt(), bathRoomParams.toInt(), floorParams.toInt(), facilityParams,
                typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams, titleParams,
                descriptionParams, changeFormatMoneyToValue(priceSellParams), "0", null, null, condition,null)
            Log.d("houseSell", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }
        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyLandArea && !isEmptyBedRoom && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear
            && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceRent &&
            typePropertyParams == "house" && typePostingParams == "rent"){
                 dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), landAreaParams.toInt(), bedRoomParams.toInt(), bathRoomParams.toInt(),
                    floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams, titleParams,
                    descriptionParams, "0", changeFormatMoneyToValue(priceRentParams), null, null, condition,null)
                Log.d("houseRent", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyLandArea && !isEmptyBedRoom && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeCertificate && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceSell && !isEmptyPriceRent
            && typePropertyParams == "house" && typePostingParams == "both"){
                 dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), landAreaParams.toInt(), bedRoomParams.toInt(), bathRoomParams.toInt(),
                    floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams,
                    titleParams, descriptionParams, priceSellParams, changeFormatMoneyToValue(priceRentParams) , null, null, condition,null)
                Log.d("houseBoth", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyBedRoom && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeCertificate && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceSell
            && typePropertyParams == "apartment" && typePostingParams == "sell"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), 0, bedRoomParams.toInt(), bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(),provinceParams, cityParams, addressParams,
                titleParams, descriptionParams, changeFormatMoneyToValue(priceSellParams), "0", null, null, condition,null)
            Log.d("ApartmentSell", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyBedRoom && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear
            && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceRent
            && typePropertyParams == "apartment" && typePostingParams == "rent"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), 0, bedRoomParams.toInt(), bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams,
                titleParams, descriptionParams, "0", changeFormatMoneyToValue(priceRentParams), null, null, condition,null)
            Log.d("ApartmentRent", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyBedRoom && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeCertificate && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceSell && !isEmptyPriceRent
            && typePropertyParams == "apartment" && typePostingParams == "both"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), 0, bedRoomParams.toInt(), bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams,
                titleParams, descriptionParams, priceSellParams, changeFormatMoneyToValue(priceRentParams), null, null, condition,null)
            Log.d("ApartmentRent", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyLandArea && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeCertificate && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceSell
            && typePropertyParams == "warehouse" && typePostingParams == "sell"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), landAreaParams.toInt(), 0, bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams,
                titleParams, descriptionParams, changeFormatMoneyToValue(priceSellParams), "0", null, null, condition,null)
            Log.d("WarehouseSell", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyLandArea && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceRent
            && typePropertyParams == "warehouse" && typePostingParams == "rent"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), landAreaParams.toInt(), 0, bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams,  addressParams,
                titleParams, descriptionParams, "0", changeFormatMoneyToValue(priceRentParams), null, null, condition,null)
            Log.d("WarehouseRent", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyLandArea && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeCertificate && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceSell
            && !isEmptyPriceSell && typePropertyParams == "warehouse" && typePostingParams == "both"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), landAreaParams.toInt(), 0, bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams,
                titleParams, descriptionParams, priceSellParams, changeFormatMoneyToValue(priceRentParams), null, null, condition,null)
            Log.d("WarehouseBoth", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeCertificate && !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceSell
            && typePropertyParams == "office" && typePostingParams == "sell"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), 0, 0, bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams,
                titleParams, descriptionParams, changeFormatMoneyToValue(priceSellParams), "0", null, null, condition,null)
            Log.d("WarehouseSell", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeProvince && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceRent
            && typePropertyParams == "office" && typePostingParams == "rent"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), 0, 0, bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams,
                titleParams, descriptionParams, "0", changeFormatMoneyToValue(priceRentParams), null, null, condition,null)
            Log.d("WarehouseRent", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }

        else if (!isEmptyTypeProperty && !isEmptyTypePosting && !isEmptyBuildingArea && !isEmptyBathRoom && !isEmptyFloor && !isEmptyYear &&
            !isEmptyTypeProvince && !isEmptyTypeCertificate && !isEmptyTypeCity && !isEmptyAddress && !isEmptyTitle && !isEmptyDescription && !isEmptyPriceRent &&
            !isEmptyPriceSell && typePropertyParams == "office" && typePostingParams == "both"){
             dataRequest = RequestDataProperty(typePropertyParams, typePostingParams, buildingAreaParams.toInt(), 0, 0, bathRoomParams.toInt(),
                floorParams.toInt(), facilityParams, typeCertificateParams, yearParams.toInt(), provinceParams, cityParams, addressParams,
                titleParams, descriptionParams, priceSellParams, changeFormatMoneyToValue(priceRentParams), null, null, condition,null)
            Log.d("WarehouseBoth", dataRequest.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataRequest", dataRequest)
            Navigation.findNavController(binding.root).navigate(R.id.action_inputDataPropertyFragment2_to_addPhotoFragment2, bundle)
        }
        else {
            Toast.makeText(requireActivity(), getString(R.string.message_fill_data), Toast.LENGTH_SHORT).show()
        }

    }

    private fun radioButtonTypeCertificate(view: View){
        val radio: RadioButton = requireActivity().findViewById(binding.includeDataPropertyTop.rdGroupCertificate.checkedRadioButtonId)
        typeCertificate = radio.text.toString()
        Log.d("certificate", typeCertificate)
    }

    override fun messageGetFacilities(msg: String) {
        Log.d("getFacilities", msg)
        progressDialog?.dismiss()
    }

    override fun messageGetProvince(msg: String) {
        Log.d("getProvince", msg)
        progressDialog?.dismiss()
    }

    override fun messageGetCity(msg: String) {
        Log.d("getCity", msg)
        progressDialog?.dismiss()
    }

    override fun getDataFacilites(data: List<DataFacilitiesProperty?>?) {
        binding.includeDataPropertyTop.rvFacility.apply {
            adapter = AdapterListFacility(requireActivity(), data, this@InputDataPropertyFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun getDataProvince(data: List<DataProvince?>?) {
        popupDialogProvince(data)
    }

    override fun getDataCity(data: List<DataCity?>?) {
        if (data != null){
            binding.includeDataPropertyTop.linearCity.visibility = View.VISIBLE
            popupDialogCity(data)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClickAdapter(view: View, data: DataFacilitiesProperty, status:Boolean) {
        when(view.id){
            R.id.cb_addon -> {
                when(status){
                    true -> {
                        listFacility.add(data.id!!)
                        val separator = ", "
                        stringFacilities = listFacility.joinToString(separator)
                    }
                    false -> {
                        listFacility.remove(data.id!!)
                    }
                }
            }
        }
    }

    override fun onClickAdapterProvince(view: View, data: DataProvince) {
        when(view.id){
            R.id.tv_province -> {
                val provinceName = data.name
                idProvince = data.id!!
                if (provinceName != binding.includeDataPropertyTop.etProvince.text.toString() && binding.includeDataPropertyTop.etCity.text.isNotEmpty()){
                    binding.includeDataPropertyTop.etCity.setText("")
                }
                binding.includeDataPropertyTop.etProvince.setText(data.name.toString())
                presenter.getCity(data.id!!)
                popupProvince?.dismiss()
            }
        }
    }

    override fun onClickAdapterCity(view: View, data: DataCity) {
        when(view.id){
            R.id.tv_province -> {
                idCity = data.id!!
                binding.includeDataPropertyTop.etCity.setText(data.name.toString())
                popupCity?.dismiss()
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.et_province -> {
                popupProvince?.show()
            }
            R.id.et_city -> {
                popupCity?.show()
            }
            R.id.btn_next -> {
                checkDataProperty()
//                Navigation.findNavController(binding.includeDataPropertyTop.btnNext).navigate(R.id.action_inputDataPropertyFragment_to_addPhotoFragment)
            }
            R.id.et_floor -> {
                typeAmountRoom = "Floor"
                popupFloor?.show()
            }
            R.id.et_bedroom -> {
                typeAmountRoom = "BedRoom"
                popupFloor?.show()
            }
            R.id.et_bathroom -> {
                typeAmountRoom = "BathRoom"
                popupFloor?.show()
            }
        }
    }

    override fun onClickAdapterAmount(view: View, data: Int) {
        when(view.id){
            R.id.tv_province -> {
                when(typeAmountRoom){
                    "Floor" -> {
                        popupFloor?.dismiss()
                        binding.includeDataPropertyTop.etFloor.setText(data.toString())
                    }
                    "BedRoom" -> {
                        popupFloor?.dismiss()
                        binding.includeDataPropertyTop.etBedroom.setText(data.toString())
                    }
                    "BathRoom" -> {
                        popupFloor?.dismiss()
                        binding.includeDataPropertyTop.etBathroom.setText(data.toString())
                    }
                }
            }
        }
    }

}