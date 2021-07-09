package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemFacilitiesHotelBinding
import haina.ecommerce.databinding.ListItemFacilitiesRoomBinding
import haina.ecommerce.model.hotels.FacilitiesItem


class AdapterFacilitiesHotelChild(val context: Context, private val listFacilities: List<String?>?):
    RecyclerView.Adapter<AdapterFacilitiesHotelChild.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemFacilitiesRoomBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(itemHaina: String?){
            with(binding){
                    tvNameFacilities.text = "$itemHaina,"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFacilitiesHotelChild.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFacilitiesRoomBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterFacilitiesHotelChild.Holder, position: Int) {
        val photo: String? = listFacilities?.get(position)
        holder.bind(photo)
    }
    override fun getItemCount(): Int = listFacilities?.size!!

}