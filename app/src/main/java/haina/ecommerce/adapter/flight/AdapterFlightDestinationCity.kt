package haina.ecommerce.adapter.flight

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.adapter.hotel.AdapterListRoomHotel
import haina.ecommerce.databinding.ListItemAddressCompanyBinding
import haina.ecommerce.databinding.ListItemDestinationCityBinding
import haina.ecommerce.model.AddressItemCompany
import haina.ecommerce.model.flight.DataAirport
import haina.ecommerce.model.flight.DestinationCity
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity


class AdapterFlightDestinationCity(val context: Context, private val listDestinationCity: List<DataAirport?>?) :
        RecyclerView.Adapter<AdapterFlightDestinationCity.Holder>() {

    private var broadcaster:LocalBroadcastManager? =null

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemDestinationCityBinding.bind(view)
        fun bind(itemHaina: DataAirport) {
            with(binding) {
                val nameCityAndCountry = "${itemHaina.city}, ${itemHaina.country}"
                binding.tvNameCityAndCountry.text = nameCityAndCountry
                binding.tvCodeCity.text = itemHaina.iata
                val nameAirport = " - ${itemHaina.name}"
                binding.tvNameAirpot.text = nameAirport
                binding.rvClick.setOnClickListener {
                    val dataIntent = Intent("dataDestination")
                            .putExtra("data", itemHaina)
                    broadcaster?.sendBroadcast(dataIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFlightDestinationCity.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemDestinationCityBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterFlightDestinationCity.Holder, position: Int) {
        val photo: DataAirport = listDestinationCity?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listDestinationCity?.size!!

}