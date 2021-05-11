package haina.ecommerce.adapter.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemBookingHotelBinding
import haina.ecommerce.databinding.ListItemUnpaidHotelBinding
import haina.ecommerce.model.hotels.transactionhotel.PaidItem


class AdapterTransactionFinish(val context: Context, private val listHotel: List<PaidItem?>?, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterTransactionFinish.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBookingHotelBinding.bind(view)
        fun bind(itemHaina: PaidItem, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvNameHotel.text = itemHaina.hotelId.toString()
//                tvCityHotel.text = itemHaina
                val checkinCheckout = "${itemHaina.checkIn} - ${itemHaina.checkOut}"
                tvCheckinCheckout.text = checkinCheckout
                val totalGuest = "${itemHaina.totalGuest} Guests,"
                tvTotalGuests.text = totalGuest
                val totalNight = "${itemHaina.totalNight} Night(s)"
                tvTotalNight.text = totalNight
                tvTypeRoom.text = itemHaina.roomId.toString()
                btnInputRating.setOnClickListener {
                    itemAdapterCallback.onClick(binding.btnInputRating, itemHaina)
                }
                cvClick.setOnClickListener {
                    itemAdapterCallback.onClick(binding.cvClick, itemHaina)
                }
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

    private fun setColorTextStatus(status: String, binding: ListItemBookingHotelBinding) {
        binding.tvStatusBooking.text = status
        if (status.contains("Waiting")) {
            binding.tvStatusBooking.setTextColor(context.resources.getColor(R.color.yellow))
        } else {
            binding.tvStatusBooking.setTextColor(context.resources.getColor(android.R.color.holo_green_dark))
        }
    }

//    private fun showRatingOrButtonRating(binding: ListItemUnpaidHotelBinding, data: PaidItem) {
////        binding.tvUserRating.text = data.reviews
////        binding.ratingBar.rating = data.rating!!
//        if (binding.tvUserRating.text == "") {
//            binding.linearRatingUser.visibility = View.GONE
//            binding.btnInputRating.visibility = View.VISIBLE
//        }
//        if(binding.tvUserRating.text != ""){
//            binding.linearRatingUser.visibility = View.VISIBLE
//            binding.btnInputRating.visibility = View.GONE
//        }
//        if (binding.tvStatusBooking.text.contains("Waiting")){
//            binding.linearRatingUser.visibility = View.GONE
//            binding.btnInputRating.visibility = View.GONE
//        }
//    }

    interface ItemAdapterCallback{
        fun onClick(view: View, data:PaidItem)
    }
}