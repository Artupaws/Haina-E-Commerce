package haina.ecommerce.view.hotels.fillindetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterDataGuest
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterSpecialRequestArray
import haina.ecommerce.databinding.FragmentFillInDetailBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.newHotel.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.room.roomdataguest.GuestDao
import haina.ecommerce.room.roomdataguest.RoomDataGuest
import haina.ecommerce.util.Constants

class FillInDetailFragment : Fragment(), View.OnClickListener, AdapterDataGuest.ItemAdapterCallback, AdapterSpecialRequestArray.ItemAdapterCallback {

    private lateinit var _binding:FragmentFillInDetailBinding
    private val binding get() = _binding
    private val helper:Helper = Helper
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private lateinit var listDataGuest: ArrayList<DataGuest>
    private lateinit var dao : GuestDao
    private lateinit var databaseGuest : RoomDataGuest
    private var contactEmail:String = ""
    private var contactName:String = ""
    private var totalGuest:Int = 0
    private var totalNight:Int? = null
    private var smokingRoomRadio:String = ""
    private var smokingBoolean:Boolean = false
    private var dataPricePolicy:DataPricePolicy? = null
    private var imageRoomUrl:String? = null
    private var listSpecialRequestArrayItem = ArrayList<SpecialRequestArrayItem>()
    private var sra = ArrayList<String>()
    private var stringRequest :String = ""
    private var specialRequestArray:Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFillInDetailBinding.inflate(inflater, container, false)
        sharedPreferenceHelper = SharedPreferenceHelper(requireActivity())
        binding.includeContactDetail.ivActionSave.setOnClickListener(this)
        binding.includeContactDetail.ivActionEdit.setOnClickListener(this)
        binding.includeDataGuest.btnAddGuest.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        databaseGuest = RoomDataGuest.getDatabase(requireActivity())
        dao = databaseGuest.getGuestDao()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbarFillindetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        dataPricePolicy = arguments?.getParcelable("dataPricePolicy")
        totalNight = arguments?.getInt("totalNight")
        imageRoomUrl = arguments?.getString("imageRoomUrl")
        setViewDataHotel(dataPricePolicy)
        setViewDataPriceRoom(dataPricePolicy)
        specialRequest(dataPricePolicy)
        getListDataGuest(databaseGuest, dao)
        radioGroup()
        binding.includeContactDetail.tvPhoneNumber.text = sharedPreferenceHelper.getValueString(Constants.PREF_PHONE_NUMBER)
        binding.includeContactDetail.tvEmailContactDetail.text = sharedPreferenceHelper.getValueString(Constants.PREF_EMAIL)
    }

    override fun onResume() {
        super.onResume()
        getListDataGuest(databaseGuest, dao)
        listSpecialRequestArrayItem.clear()
    }

    private fun setViewDataHotel(data:DataPricePolicy?){
        binding.includeViewHotelChoosed.tvNameHotel.text = data?.hotelName
        binding.includeViewHotelChoosed.tvCheckIn.text = data?.checkInDate
        binding.includeViewHotelChoosed.tvCheckOut.text = data?.checkOutDate
        binding.includeViewHotelChoosed.tvNameCityHotel.text = data?.cityName
        val nameRoom = "(1x) ${data?.roomName}"
        binding.includeViewHotelChoosed.tvNameRoom.text = nameRoom
        val cancelPolicy = HtmlCompat.fromHtml("${data?.cancelPolicy}", HtmlCompat.FROM_HTML_MODE_LEGACY)
        if (cancelPolicy.isNullOrEmpty()){
            binding.includeViewHotelChoosed.linearCancelPolicy.visibility = View.GONE
        } else {
            binding.includeViewHotelChoosed.linearCancelPolicy.visibility = View.VISIBLE
            binding.includeViewHotelChoosed.tvCancelPolicy.text = cancelPolicy
        }
        val additionalInformation = HtmlCompat.fromHtml("${data?.additionalInformation}", HtmlCompat.FROM_HTML_MODE_LEGACY)
        if (additionalInformation.isNullOrEmpty()){
            binding.includeViewHotelChoosed.linearAdditionalInformation.visibility = View.GONE
        } else {
            binding.includeViewHotelChoosed.linearAdditionalInformation.visibility = View.VISIBLE
            binding.includeViewHotelChoosed.tvAdditionalInformation.text = additionalInformation
        }
        totalGuest = if (nameRoom.toLowerCase().contains("twin") ||nameRoom.toLowerCase().contains("double")){
            2
        } else if (nameRoom.toLowerCase().contains("single")){
            1
        } else {
            4
        }
    }

    private fun setViewDataPriceRoom(data: DataPricePolicy?){
        val priceRoom = helper.convertToFormatMoneyIDRFilter(data?.totalPrice.toString())
        binding.includePriceDetail.tvTotalPrice.text = priceRoom
    }

    private fun specialRequest(data: DataPricePolicy?){
        Log.d("dataSRA", data?.specialRequestArray?.size.toString())
        if (data?.specialRequestArray?.size == null){
            specialRequestArray = false
            binding.includeSpecialRequest.rvRequest.visibility = View.GONE
            binding.includeSpecialRequest.linearAddRequest.visibility = View.VISIBLE
        } else {
            specialRequestArray = true
            binding.includeSpecialRequest.rvRequest.visibility = View.VISIBLE
            binding.includeSpecialRequest.linearAddRequest.visibility = View.GONE
            binding.includeSpecialRequest.rvRequest.apply {
                adapter = AdapterSpecialRequestArray(requireActivity(), data.specialRequestArray, this@FillInDetailFragment, true)
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun getListDataGuest(database: RoomDataGuest, dao: GuestDao) {
        listDataGuest = arrayListOf()
        listDataGuest.addAll(dao.getAll())
        setupListDataGuest(listDataGuest)
        if (listDataGuest.size == totalGuest) {
            binding.includeDataGuest.btnAddGuest.visibility = View.GONE
        } else {
            binding.includeDataGuest.btnAddGuest.visibility = View.VISIBLE
        }
    }

    private fun setupListDataGuest(listParams: ArrayList<DataGuest>) {
        binding.includeDataGuest.rvGuest.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = AdapterDataGuest(requireActivity(), listParams, this@FillInDetailFragment, true)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_action_edit -> {
                editContactDetail()
            }
            R.id.iv_action_save -> {
                cancelSaveContactDetail()
            }
            R.id.btn_add_guest -> {
                val bundle = Bundle()
                bundle.putString("hotel","setupHotel")
                Navigation.findNavController(binding.includeDataGuest.btnAddGuest).navigate(R.id.action_fillInDetailFragment_to_detailFillDataPassengerFragment2, bundle)
            }
            R.id.btn_next -> {
                if (specialRequestArray){
                    checkDataBookingRequestArray()
                } else {
                    checkDataBookingRequestString()
                }
            }
        }
    }

    private fun editContactDetail(){
        binding.includeContactDetail.ivActionEdit.visibility = View.GONE
        binding.includeContactDetail.ivActionSave.visibility = View.VISIBLE
        binding.includeContactDetail.linearStateUnsaved.visibility =View.VISIBLE
        binding.includeContactDetail.linearStateSaved.visibility =View.GONE
    }

    private fun saveContactDetail(){
        binding.includeContactDetail.ivActionEdit.visibility = View.VISIBLE
        binding.includeContactDetail.ivActionSave.visibility = View.GONE
        binding.includeContactDetail.linearStateUnsaved.visibility =View.GONE
        binding.includeContactDetail.linearStateSaved.visibility =View.VISIBLE
        requireActivity().currentFocus?.let { view ->
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun cancelSaveContactDetail(){
        if (binding.includeContactDetail.etNameContactDetail.text.toString()
            == sharedPreferenceHelper.getValueString(Constants.PREF_FULLNAME) &&
            binding.includeContactDetail.etEmailContactDetail.text.toString()
            == sharedPreferenceHelper.getValueString(Constants.PREF_EMAIL)){
            saveContactDetail()
        } else {
            checkDataContactDetail()
        }
    }

    private fun checkDataContactDetail(){
        var email = binding.includeContactDetail.etEmailContactDetail.text.toString()
        var name = binding.includeContactDetail.etNameContactDetail.text.toString()
        var isEmptyEmail = true
        var isEmptyName = true

        if (email.isNullOrEmpty()){
            isEmptyEmail = true
            binding.includeContactDetail.etEmailContactDetail.error = "Can't empty"
        } else {
            isEmptyEmail = false
            email = binding.includeContactDetail.etEmailContactDetail.text.toString()
        }

        if (name.isNullOrEmpty()){
            isEmptyName = true
            binding.includeContactDetail.etNameContactDetail.error = "Can't empty"
        } else {
            isEmptyName = false
            name = binding.includeContactDetail.etNameContactDetail.text.toString()
        }

        if (!isEmptyEmail && !isEmptyName){
            saveContactDetail()
            contactEmail = email
            contactName = name
            binding.includeContactDetail.tvEmailContactDetail.text = contactEmail
            binding.includeContactDetail.tvPhoneNumber.text = contactName
        } else {
            Toast.makeText(requireActivity(), "Please complete form", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View, data: DataGuest) {
        when (view.id) {
            R.id.tv_action_delete -> {
                deleteGuest(DataGuest(data.id, data.title, data.first_name, data.last_name))
                onResume()
            }
        }
    }

    private fun radioGroup(){
        binding.rdSmokingRoom.setOnCheckedChangeListener { _, i ->
            val radio: RadioButton = requireActivity().findViewById(i)
            radioButtonSmokingRoom(radio)
        }
    }

    private fun radioButtonSmokingRoom(view: View){
        val radio: RadioButton = requireActivity().findViewById(binding.rdSmokingRoom.checkedRadioButtonId)
        smokingRoomRadio = radio.text.toString()
        smokingBoolean = smokingRoomRadio.toLowerCase().contains("yes")
    }

    private fun deleteGuest(dataguest: DataGuest){
        dao.delete(dataguest)
    }

    private fun deleteAllGuest() {
        dao.deleteAll()
    }

    private fun checkDataBookingRequestString(){
        var isEmptySmokingRoom = true
        var isEmptyDataGuest = true
        var isEmptySpecialRequest = true

        var smokingRoom = smokingRoomRadio
        var listGuest = listDataGuest
        var specialRequest = binding.includeSpecialRequest.etSpecialRequest.text.toString()

        if (smokingRoom.isNullOrEmpty()){
            isEmptySmokingRoom = true
        } else {
            isEmptySmokingRoom = false
            smokingRoom = smokingRoomRadio
        }

        if (listDataGuest.size == 0){
            isEmptyDataGuest = true
        } else {
            isEmptyDataGuest = false
            listGuest = listDataGuest
        }

        if (specialRequest.isNullOrEmpty()){
            specialRequest = ""
            isEmptySpecialRequest = false
        } else {
            specialRequest = binding.includeSpecialRequest.etSpecialRequest.text.toString()
            isEmptySpecialRequest = false
        }
        if (!isEmptySmokingRoom && !isEmptyDataGuest && !isEmptySpecialRequest && !specialRequestArray){
            val dataBooking = RequestBookingHotelDarma(smokingBoolean, binding.includeContactDetail.tvPhoneNumber.text.toString(), specialRequest, null, listGuest,
                 binding.includeContactDetail.tvEmailContactDetail.text.toString(), binding.includePriceDetail.tvTotalPrice.text.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataBooking", dataBooking)
            bundle.putParcelable("dataPricePolicy", dataPricePolicy)
            bundle.putString("imageRoomUrl", imageRoomUrl)
            totalNight?.let { bundle.putInt("totalNight", it) }
            Navigation.findNavController(binding.btnNext).navigate(R.id.action_fillInDetailFragment_to_reviewBookingFragment, bundle)
            Log.d("dataRequest", "$smokingRoom, ${binding.includeContactDetail.tvPhoneNumber.text}, ${binding.includeContactDetail.tvEmailContactDetail.text}," +
                    "$specialRequest, $listSpecialRequestArrayItem, $listGuest")
        }else{
            Log.d("dataRequest", "$smokingRoom, ${binding.includeContactDetail.tvPhoneNumber.text}, ${binding.includeContactDetail.tvEmailContactDetail.text}," +
                    "$specialRequest, $listGuest")
            Toast.makeText(requireActivity(), "Please complete all data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkDataBookingRequestArray(){
        var isEmptySmokingRoom = true
        var isEmptyDataGuest = true
        var isEmptySpecialRequest = true

        var smokingRoom = smokingRoomRadio
        var listGuest = listDataGuest
        val specialRequest = listSpecialRequestArrayItem

        if (smokingRoom.isNullOrEmpty()){
            isEmptySmokingRoom = true
        } else {
            isEmptySmokingRoom = false
            smokingRoom = smokingRoomRadio
        }

        if (listDataGuest.size == 0){
            isEmptyDataGuest = true
        } else {
            isEmptyDataGuest = false
            listGuest = listDataGuest
        }

        isEmptySpecialRequest = specialRequest.size == 0

        if (!isEmptySmokingRoom && !isEmptyDataGuest && !isEmptySpecialRequest){
            val dataBooking = RequestBookingHotelDarma(smokingBoolean, binding.includeContactDetail.tvPhoneNumber.text.toString(), stringRequest, null, listGuest,
                binding.includeContactDetail.tvEmailContactDetail.text.toString(),  binding.includePriceDetail.tvTotalPrice.text.toString())
            val bundle = Bundle()
            bundle.putParcelable("dataBooking", dataBooking)
            bundle.putParcelable("dataPricePolicy", dataPricePolicy)
            bundle.putString("imageRoomUrl", imageRoomUrl)
            totalNight?.let { bundle.putInt("totalNight", it) }
            Navigation.findNavController(binding.btnNext).navigate(R.id.action_fillInDetailFragment_to_reviewBookingFragment, bundle)
            Log.d("dataRequestArray", "$smokingRoom, ${binding.includeContactDetail.tvPhoneNumber.text}, ${binding.includeContactDetail.tvEmailContactDetail.text}," +
                    "${listSpecialRequestArrayItem}, $listGuest")
        }else{
            Log.d("dataRequestArray", "$smokingRoom, ${binding.includeContactDetail.tvPhoneNumber.text}, ${binding.includeContactDetail.tvEmailContactDetail.text}," +
                    "$specialRequest, $listGuest")
            Toast.makeText(requireActivity(), "Please complete all data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClickSpecialRequest(view: View, data: SpecialRequestArrayItem, status:Boolean) {
        when(view.id){
            R.id.cb_addon -> {
                when (status){
                    true -> {
                        listSpecialRequestArrayItem.add(data)
                        sra.add(data.iD!!)
                        val separator = ", "
                        stringRequest = sra.joinToString(separator)
                    }
                    false -> {
                        listSpecialRequestArrayItem.remove(data)
                        sra.remove(data.iD!!)
                    }
                }
            }
        }
    }

}