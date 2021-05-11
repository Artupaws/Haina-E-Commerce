package haina.ecommerce.adapter.hotel

import android.content.Context
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
import haina.ecommerce.model.hotels.transactionhotel.PaidItem


class AdapterTransactionFinish(val context: Context, private val listHotel: List<PaidItem?>?, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterTransactionFinish.Holder>() {

    private val helper:Helper = Helper

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBookingHotelBinding.bind(view)
        fun bind(itemHaina: PaidItem, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvHotelName.text = itemHaina.hotel?.hotelName
                tvAddressHotel.text = itemHaina.hotel?.hotelAddress
                val priceAndNight = "${helper.convertToFormatMoneyIDRFilter(itemHaina.totalPrice.toString())} (${itemHaina.totalNight}) Night"
                tvPriceAndTotalNight.text = priceAndNight
                val checkinCheckout = "${itemHaina.checkIn} - ${itemHaina.checkOut}"
                tvCheckinCheckout.text = checkinCheckout
                Glide.with(context).load(itemHaina.hotel?.hotelImage).into(ivImageRoom)
                tvIdBooking.text = itemHaina.id.toString()
                tvTotalGuest.text = itemHaina.totalGuest.toString()
                showRatingOrButtonRating(binding, itemHaina)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTransactionFinish.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBookingHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionFinish.Holder, position: Int) {
        val photo: PaidItem = listHotel?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listHotel?.size!!

    private fun setColorTextStatus(status: String, binding: ListItemFinishTransactionBinding) {
//        binding.tvStatusBooking.text = status
//        if (status.contains("Waiting")) {
//            binding.tvStatusBooking.setTextColor(context.resources.getColor(R.color.yellow))
//        } else {
//            binding.tvStatusBooking.setTextColor(context.resources.getColor(android.R.color.holo_green_dark))
//        }
    }

    private fun showRatingOrButtonRating(binding: ListItemBookingHotelBinding, data: PaidItem) {
//        binding.tvUserRating.text = data.reviews
//        binding.ratingBar.rating = data.rating!!
        if (binding.tvUserRating.text == "") {
            binding.linearRatingUser.visibility = View.GONE
            binding.btnInputRating.visibility = View.VISIBLE
        }
        if(binding.tvUserRating.text != ""){
            binding.linearRatingUser.visibility = View.VISIBLE
            binding.btnInputRating.visibility = View.GONE
        }
        if (data.status?.contains("Waiting")!!){
            binding.linearRatingUser.visibility = View.GONE
            binding.btnInputRating.visibility = View.GONE
        }
    }

    interface ItemAdapterCallback{
        fun onClick(view: View, data:PaidItem)
    }
}