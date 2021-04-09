package haina.ecommerce.adapter.hotel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemLocationHotelsBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.hotels.LocationHotels
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity


class AdapterListLocationHotel(val context: Context, private val listLocationHotel: List<LocationHotels?>?):
    RecyclerView.Adapter<AdapterListLocationHotel.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemLocationHotelsBinding.bind(view)
        fun bind(itemHaina: LocationHotels){
            with(binding) {
                tvNameLocation.text = itemHaina.locationHotels
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListLocationHotel.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemLocationHotelsBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListLocationHotel.Holder, position: Int) {
        val photo: LocationHotels = listLocationHotel?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listLocationHotel?.size!!


}