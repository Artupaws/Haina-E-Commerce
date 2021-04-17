package haina.ecommerce.adapter.hotel

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemBookingHotelBinding
import haina.ecommerce.databinding.ListItemHotelsBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.hotels.Bookings
import haina.ecommerce.model.hotels.Hotels
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity
import haina.ecommerce.view.hotels.DetailHotelsActivity
import haina.ecommerce.view.hotels.bookings.DetailBookingsActivity


class AdapterListBooking(val context: Context, private val listHotel: List<Bookings?>?) :
        RecyclerView.Adapter<AdapterListBooking.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBookingHotelBinding.bind(view)
        fun bind(itemHaina: Bookings) {
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
                cvClick.setOnClickListener {
                    val intent = Intent(context, DetailBookingsActivity::class.java)
                            .setFlags(FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
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
        holder.bind(photo)
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
}