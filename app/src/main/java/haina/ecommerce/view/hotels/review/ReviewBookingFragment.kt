package haina.ecommerce.view.hotels.review

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
import haina.ecommerce.databinding.FragmentReviewBookingBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.DataBooking
import haina.ecommerce.model.hotels.newHotel.DataGuest
import haina.ecommerce.model.hotels.newHotel.DataPricePolicy
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotelDarma

class ReviewBookingFragment : Fragment(), AdapterDataGuest.ItemAdapterCallback {

    private lateinit var _binding:FragmentReviewBookingBinding
    private val binding get() = _binding
    private val helper:Helper = Helper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentReviewBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val totalNight = arguments?.getInt("totalNight")
        val dataBooking = arguments?.getParcelable<RequestBookingHotelDarma>("dataBooking")
        val imageRoomUrl = arguments?.getString("imageRoomUrl")
        val dataPricePolicy = arguments?.getParcelable<DataPricePolicy>("dataPricePolicy")
        val totalGuest = dataBooking?.paxes?.size
        imageRoomUrl?.let { setViewDataHotel(it, dataPricePolicy!!, totalGuest!!, dataBooking, totalNight!!) }

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
            adapter = AdapterDataGuest(requireActivity(), dataBooking.paxes, this@ReviewBookingFragment)
        }

        binding.tvTotalPrice.text = helper.convertToFormatMoneyIDRFilter(dataPricePolicy.totalPrice.toString())
    }

    override fun onClick(view: View, data: DataGuest) {
        TODO("Not yet implemented")
    }

}