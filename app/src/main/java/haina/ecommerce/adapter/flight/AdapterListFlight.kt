package haina.ecommerce.adapter.flight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemFlightAddOnBinding
import haina.ecommerce.model.flight.BaggageInfosItem
import haina.ecommerce.model.flight.MealInfosItem
import haina.ecommerce.model.flight.Ticket
import java.util.*


class AdapterListFlight(val context: Context, private val listAirlines: MutableList<Ticket>?,
                        private val dataAddOn: List<BaggageInfosItem?>?,
                        private val dataMeals: List<MealInfosItem?>?) :
        RecyclerView.Adapter<AdapterListFlight.Holder>() {
    private var broadcaster : LocalBroadcastManager? = null

    @Suppress("UNCHECKED_CAST")
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
                    intentOpenDialogAddOn.putParcelableArrayListExtra("openDialog", dataAddOn as ArrayList<BaggageInfosItem>)
                    intentOpenDialogAddOn.putParcelableArrayListExtra("openDialogMeals", dataMeals as ArrayList<MealInfosItem>)
                    broadcaster?.sendBroadcast(intentOpenDialogAddOn)
                }
                btnChooseSeat.setOnClickListener {
                    val intentChooseSeat = Intent("chooseSeat")
                    broadcaster?.sendBroadcast(intentChooseSeat)
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
}