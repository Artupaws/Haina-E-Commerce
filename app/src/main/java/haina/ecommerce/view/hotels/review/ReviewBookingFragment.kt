package haina.ecommerce.view.hotels.review

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterDataGuest
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterSpecialRequestArray
import haina.ecommerce.databinding.FragmentReviewBookingBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.newHotel.DataGuest
import haina.ecommerce.model.hotels.newHotel.DataPricePolicy
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotelDarma
import haina.ecommerce.model.hotels.newHotel.SpecialRequestArrayItem
import haina.ecommerce.view.paymentmethod.PaymentActivity

class ReviewBookingFragment : Fragment(), AdapterDataGuest.ItemAdapterCallback, AdapterSpecialRequestArray.ItemAdapterCallback, View.OnClickListener {

    private lateinit var _binding:FragmentReviewBookingBinding
    private val binding get() = _binding
    private val helper:Helper = Helper
    private var dataBooking:RequestBookingHotelDarma? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentReviewBookingBinding.inflate(inflater, container, false)
        binding.btnPayment.setOnClickListener(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val totalNight = arguments?.getInt("totalNight")
        dataBooking = arguments?.getParcelable("dataBooking")
        val imageRoomUrl = arguments?.getString("imageRoomUrl")
        val dataPricePolicy = arguments?.getParcelable<DataPricePolicy>("dataPricePolicy")
        val totalGuest = dataBooking?.paxes?.size
        imageRoomUrl?.let { setViewDataHotel(it, dataPricePolicy!!, totalGuest!!, dataBooking!!, totalNight!!) }

        binding.toolbar5.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setViewDataHotel(imageRoomUrl:String, dataPricePolicy: DataPricePolicy, totalGuest:Int, dataBooking: RequestBookingHotelDarma, totalNight:Int){
        binding.includeReviewHotel.tvNameHotel.text = dataPricePolicy.hotelName
        val totalGuestParams = "Total Guest $totalGuest"
        binding.includeReviewHotel.tvTotalGuest.text = totalGuestParams
        binding.includeReviewHotel.tvCheckIn.text = dataPricePolicy.checkInDate
        binding.includeReviewHotel.tvCheckOut.text = dataPricePolicy.checkOutDate
        val totalNightParams = "$totalNight Night"
        binding.includeReviewHotel.tvTotalNight.text = totalNightParams
        val nameRoom = "(1x) ${dataPricePolicy.roomName}"
        binding.includeReviewHotel.tvNameRoom.text = nameRoom
        var breakfastParams = dataPricePolicy.breakfast
        breakfastParams = if (!breakfastParams?.toLowerCase()?.contains("breakfast")!!){
            "Breakfast : ${requireActivity().getString(R.string.breakfast_status_no)}"
        } else {
            "Breakfast : ${requireActivity().getString(R.string.breakfast_status_yes)}"
        }
        binding.includeReviewHotel.tvStatusBreakfast.text = breakfastParams

        val cancelPolicy = HtmlCompat.fromHtml("${dataPricePolicy.cancelPolicy}", HtmlCompat.FROM_HTML_MODE_LEGACY)
        if (cancelPolicy.isNullOrEmpty()){
            binding.includeReviewHotel.linearCancelPolicy.visibility = View.GONE
        } else {
            binding.includeReviewHotel.linearCancelPolicy.visibility = View.VISIBLE
            binding.includeReviewHotel.tvCancelPolicy.text = cancelPolicy
        }

        val additionalInformation = HtmlCompat.fromHtml("${dataPricePolicy.additionalInformation}", HtmlCompat.FROM_HTML_MODE_LEGACY)
        if (additionalInformation.isNullOrEmpty()){
            binding.includeReviewHotel.linearAdditionalInformation.visibility = View.GONE
        } else {
            binding.includeReviewHotel.linearAdditionalInformation.visibility = View.VISIBLE
            binding.includeReviewHotel.tvAdditionalInformation.text = additionalInformation
        }

        binding.includeReviewHotel.rvGuest.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = AdapterDataGuest(requireActivity(), dataBooking.paxes, this@ReviewBookingFragment, false)
        }

        if (dataBooking.specialRequest != null){
            binding.includeReviewHotel.linearAddRequest.visibility = View.VISIBLE
            binding.includeReviewHotel.etSpecialRequest.setText(dataBooking.specialRequest)
            binding.includeReviewHotel.rvRequest.visibility = View.GONE
            binding.includeReviewHotel.btnAddRequest.visibility = View.GONE
        }
//        else {
//            binding.includeReviewHotel.linearAddRequest.visibility = View.GONE
//            binding.includeReviewHotel.rvRequest.visibility = View.VISIBLE
//            binding.includeReviewHotel.rvRequest.apply {
//                adapter = AdapterSpecialRequestArray(requireActivity(), dataBooking.special_request_array_complete, this@ReviewBookingFragment, false)
//                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//            }
        binding.tvTotalPrice.text = helper.convertToFormatMoneyIDRFilter(dataPricePolicy.totalPrice.toString())
    }

    override fun onClick(view: View, data: DataGuest) {
        TODO("Not yet implemented")
    }

    override fun onClickSpecialRequest(view: View, dataRequest: SpecialRequestArrayItem, status: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_payment -> {
                val intentPayment = Intent(requireActivity(), PaymentActivity::class.java)
                intentPayment.putExtra("dataBooking", dataBooking)
                intentPayment.putExtra("typeTransaction", 4)
                startActivity(intentPayment)
            }
        }
    }

}