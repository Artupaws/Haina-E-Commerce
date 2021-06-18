package haina.ecommerce.adapter.flight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFlightAddOnBinding
import haina.ecommerce.model.flight.AirlinesFirst
import haina.ecommerce.model.flight.Ticket

class AdapterListFlight(val context: Context, private val listAirlines: MutableList<Ticket>?) :
        RecyclerView.Adapter<AdapterListFlight.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemFlightAddOnBinding.bind(view)
        fun bind(itemHaina: Ticket) {
            with(binding) {
                tvAirlineCode.text = itemHaina.nameAirlines
                val originDestination = "${itemHaina.cityCodeDeparture} - ${itemHaina.cityCodeArrived}"
                tvOriginDestination.text = originDestination
                val originDestinationTime = "(${itemHaina.departureTime.substring(11, 19)} - ${itemHaina.arrivedTime.substring(11, 19)})"
                tvTimeFlight.text = originDestinationTime
                val numberFlight = "FLIGHT ${adapterPosition + 1}"
                tvTypeFlight.text = numberFlight
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListFlight.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFlightAddOnBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListFlight.Holder, position: Int) {
        val photo: Ticket = listAirlines?.get(position)!!
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listAirlines?.size!!

    interface ItemAdapterCallback{
        fun onClick(view:View, data:Ticket)
    }

}