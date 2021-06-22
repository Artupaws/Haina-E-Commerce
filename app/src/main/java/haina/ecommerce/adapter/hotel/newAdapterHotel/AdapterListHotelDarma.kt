package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import haina.ecommerce.databinding.ListItemHotelsBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.DataHotel
import haina.ecommerce.model.hotels.newHotel.HotelsItem


class AdapterListHotelDarma(val context: Context, private val listHotel: List<HotelsItem?>?,
                            private val itemAdapterCallback:ItemAdapterCallBack):
    RecyclerView.Adapter<AdapterListHotelDarma.Holder>() {

    private val helper:Helper = Helper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemHotelsBinding.bind(view)
        fun bind(hotelHaina: HotelsItem, itemAdapterCallback:ItemAdapterCallBack){
            with(binding){
                tvNameHotel.text = hotelHaina.name
                tvLocationHotel.text = hotelHaina.address
                val startPrice = helper.convertToFormatMoneyIDRFilter(hotelHaina.priceStart.toString())
                tvStartPrice.text = startPrice
                cvClick.setOnClickListener { itemAdapterCallback.onClick(binding.cvClick, hotelHaina.iD!!) }
                val logo = "${hotelHaina.logo}"
                Picasso.get().load(logo).into(ivHotels)
//                Glide.with(context).load(logo).into(ivHotels)
                ratingBarHotel.rating = hotelHaina.rating!!
                val avgRating = "${hotelHaina.ratingAverage} - Avg Rating"
                tvAvgRating.text = avgRating
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListHotelDarma.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemHotelsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListHotelDarma.Holder, position: Int) {
        val photo: HotelsItem = listHotel?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listHotel?.size!!

    interface ItemAdapterCallBack{
        fun onClick(view: View, idHotel: String)
    }


}