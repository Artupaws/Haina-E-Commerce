package haina.ecommerce.adapter.hotel

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemBookingHotelBinding
import haina.ecommerce.model.hotels.Bookings
import haina.ecommerce.view.hotels.transactionhotel.DetailBookingsActivity


class AdapterListBooking(val context: Context, private val listHotel: List<Bookings?>?, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterListBooking.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBookingHotelBinding.bind(view)
        fun bind(itemHaina: Bookings, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
                tvNameHotel.text = itemHaina.nameHotel
                tvCityHotel.text = itemHaina.cityHotel
                val checkinCheckout = "${itemHaina.checkInDate} - ${itemHaina.checkoutDate}"
                tvCheckinCheckout.text = checkinCheckout
                val totalGuest = "${itemHaina.totalGuests} Guests,"
                tvTotalGuests.text = totalGuest
                val totalNight = "${itemHaina.totalNight} Night(s)"
                tvTotalNight.text = totalNight
                tvTypeRoom.text = itemHaina.typeRoom
                setColorTextStatus(itemHaina.bookingStatus, binding)
                showRatingOrButtonRating(binding, itemHaina)
                btnInputRating.setOnClickListener {
                    itemAdapterCallback.onClick(binding.btnInputRating, itemHaina)
                }
                cvClick.setOnClickListener {
                    itemAdapterCallback.onClick(binding.cvClick, itemHaina)
                }
//                cvClick.setOnClickListener {
//                    val intent = Intent(context, DetailBookingsActivity::class.java)
//                            .setFlags(FLAG_ACTIVITY_NEW_TASK)
//                    context.startActivity(intent)
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListBooking.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBookingHotelBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListBooking.Holder, position: Int) {
        val photo: Bookings = listHotel?.get(position)!!
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

    private fun showRatingOrButtonRating(binding: ListItemBookingHotelBinding, data: Bookings) {
        binding.tvUserRating.text = data.reviews
        binding.ratingBar.rating = data.rating!!
        if (binding.tvUserRating.text == "") {
            binding.linearRatingUser.visibility = View.GONE
            binding.btnInputRating.visibility = View.VISIBLE
        }
        if(binding.tvUserRating.text != ""){
            binding.linearRatingUser.visibility = View.VISIBLE
            binding.btnInputRating.visibility = View.GONE
        }
        if (binding.tvStatusBooking.text.contains("Waiting")){
            binding.linearRatingUser.visibility = View.GONE
            binding.btnInputRating.visibility = View.GONE
        }
    }

    interface ItemAdapterCallback{
        fun onClick(view: View, data:Bookings)
    }
}