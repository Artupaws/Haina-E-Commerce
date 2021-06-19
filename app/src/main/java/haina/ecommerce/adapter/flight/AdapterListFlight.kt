package haina.ecommerce.adapter.flight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFlightAddOnBinding
import haina.ecommerce.model.flight.AirlinesFirst
import haina.ecommerce.model.flight.Ticket

class AdapterListFlight(val context: Context, private val listAirlines: MutableList<Ticket>?) :
        RecyclerView.Adapter<AdapterListFlight.Holder>() {
    private var broadcaster : LocalBroadcastManager? = null

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
                btnAddOn.setOnClickListener {
                val intentOpenDialogAddOn = Intent("addOn")
                intentOpenDialogAddOn.putExtra("openDialog","open")
                    broadcaster?.sendBroadcast(intentOpenDialogAddOn)
                }
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
        broadcaster = LocalBroadcastManager.getInstance(context)
        holder.bind(photo)
    }

    override fun getItemCount(): Int = listAirlines?.size!!

    interface ItemAdapterCallback{
        fun onAdapterClick(view:View, data:Ticket)
    }

}