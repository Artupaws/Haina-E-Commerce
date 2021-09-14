package haina.ecommerce.adapter.hotel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemBookingHotelBinding
import haina.ecommerce.databinding.ListItemFinishTransactionBinding
import haina.ecommerce.databinding.ListItemUnpaidHotelBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.Helper.convertLongtoTime
import haina.ecommerce.model.hotels.transactionhotel.PaidItem
import java.util.*


class AdapterTransactionFinish(val context: Context,
                               private val listHotel: List<haina.ecommerce.model.hotels.newHotel.PaidItem?>?,
                               private val itemAdapterCallback: ItemAdapterCallback, private val tab:String) :
        RecyclerView.Adapter<AdapterTransactionFinish.Holder>() {

    private val helper:Helper = Helper
    private val now = Calendar.getInstance().get(Calendar.DATE)

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBookingHotelBinding.bind(view)
        fun bind(itemHaina: haina.ecommerce.model.hotels.newHotel.PaidItem, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvHotelName.text = itemHaina.hotel?.hotelName
                tvAddressHotel.text = itemHaina.hotel?.hotelAddress
                val priceAndNight = "${helper.convertToFormatMoneyIDRFilter(itemHaina.totalPrice.toString())} (${itemHaina.totalNight}) Night"
                tvPriceAndTotalNight.text = priceAndNight
                val checkinCheckout = "${itemHaina.checkIn} to ${itemHaina.checkOut}"
                tvCheckinCheckout.text = checkinCheckout
//                Glide.with(context).load(itemHaina.hotel?.hotelImage).into(ivImageRoom)
                when (tab) {
                    "cancel" -> {
                        lnReservationNo.visibility=View.GONE
                    }
                    "finish" -> {
                        tvIdBooking.text = itemHaina.reservationNo?.toString()

                    }
                }
//                tvTotalGuest.text = itemHaina.totalGuest.toString()

            }
        } 
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTransactionFinish.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBookingHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionFinish.Holder, position: Int) {
        val photo: haina.ecommerce.model.hotels.newHotel.PaidItem = listHotel?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listHotel?.size!!

//    private fun setColorTextStatus(status: String, binding: ListItemFinishTransactionBinding) {
//        binding.tvStatusBooking.text = status
//        if (status.contains("Waiting")) {
//            binding.tvStatusBooking.setTextColor(context.resources.getColor(R.color.yellow))
//        } else {
//            binding.tvStatusBooking.setTextColor(context.resources.getColor(android.R.color.holo_green_dark))
//        }
//    }
//
//    private fun showRatingOrButtonRating(binding: ListItemBookingHotelBinding, data: haina.ecommerce.model.hotels.newHotel.PaidItem) {
//        val dateCheckout = helper.getOnlyDateFromStringDate(data.checkOut!!)
//        Log.d("resultConvert", dateCheckout)
//        if (now >= dateCheckout.toInt()){
//            if (data.rating == null){
//                binding.linearRatingUser.visibility = View.GONE
//                binding.btnInputRating.visibility = View.VISIBLE
//            } else {
//                binding.linearRatingUser.visibility = View.VISIBLE
//                binding.btnInputRating.visibility = View.GONE
//                binding.ratingBar.rating = data.rating.rating?.toFloat()!!
//                binding.tvUserRating.text = data.rating.review
//                binding.tvDateReview.text = data.createdAt?.substring(0,10)
//            }
//        }
//        if (now >= dateCheckout.toInt()){
//            binding.linearRatingUser.visibility = View.VISIBLE
//            binding.btnInputRating.visibility = View.GONE
//            binding.ratingBar.rating = data.rating?.rating!!.toFloat()
//            binding.tvUserRating.text = data.rating.review
//            binding.tvDateReview.text = data.createdAt?.substring(0,10)
//
//        } else {
//            binding.linearRatingUser.visibility = View.GONE
//            binding.btnInputRating.visibility = View.GONE
//        }
//        if (binding.tvUserRating.text == "") {
//            binding.linearRatingUser.visibility = View.GONE
//            binding.btnInputRating.visibility = View.VISIBLE
//        }
//        if(binding.tvUserRating.text != ""){
//            binding.linearRatingUser.visibility = View.VISIBLE
//            binding.btnInputRating.visibility = View.GONE
//        }
//        if (data.status?.contains("Waiting")!!){
//            binding.linearRatingUser.visibility = View.GONE
//            binding.btnInputRating.visibility = View.GONE
//        }
//    }

    interface ItemAdapterCallback{
        fun onClick(view: View, data:haina.ecommerce.model.hotels.newHotel.PaidItem)
    }
}