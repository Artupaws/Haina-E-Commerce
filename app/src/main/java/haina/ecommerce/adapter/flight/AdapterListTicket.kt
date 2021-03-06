package haina.ecommerce.adapter.flight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemTicketChooseBinding
import haina.ecommerce.model.flight.AirlinesFirst
import haina.ecommerce.model.flight.Ticket

class AdapterListTicket(val context: Context, private val listAirlines: MutableList<Ticket>?,
                        private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterListTicket.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemTicketChooseBinding.bind(view)
        fun bind(itemHaina: Ticket, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
                tvAirlineName.text = itemHaina.nameAirlines
                val destination = "${itemHaina.cityCodeDeparture} - ${itemHaina.cityCodeArrived}"
                tvDestination.text = destination
                val schedule = "${itemHaina.departureTime.substring(11, 19)} - ${itemHaina.arrivedTime.substring(11, 19)}"
                tvSchedule.text = schedule
                tvTypeFlight.text = itemHaina.typeFlight
                itemView.setOnClickListener { itemAdapterCallback.onClick(itemView, itemHaina) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListTicket.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTicketChooseBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListTicket.Holder, position: Int) {
        val photo: Ticket = listAirlines?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listAirlines?.size!!


    interface ItemAdapterCallback{
        fun onClick(view:View, data:Ticket)
    }

}